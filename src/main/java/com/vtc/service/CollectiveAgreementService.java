package com.vtc.service;

import java.time.LocalDate;
import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.AgreementChange;
import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.model.user.Administrator;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.CollectiveAgreementDao;
import com.vtc.persistence.jpa.CollectiveAgreementDaoJpa;

import jakarta.persistence.EntityManager;

public class CollectiveAgreementService {

    private final CollectiveAgreementDao agreementDao;

    public CollectiveAgreementService() { this.agreementDao = new CollectiveAgreementDaoJpa(); }

    public CollectiveAgreementService(CollectiveAgreementDao agreementDao) { this.agreementDao = agreementDao; }

    public void createAgreement(CollectiveAgreement agreement) { agreementDao.create(agreement); }
    public void updateAgreement(CollectiveAgreement agreement) { agreementDao.update(agreement); }
    public void createOrUpdateAgreement(CollectiveAgreement agreement) { agreementDao.createOrUpdate(agreement); }
    public void deleteAgreement(Long id) { agreementDao.delete(id); }

    public List<CollectiveAgreement> listAgreements() { return agreementDao.findAll(); }
    public CollectiveAgreement getAgreement(Long id) { return agreementDao.findById(id); }

    /**
     * Load agreement with annexes and initialize bonuses safely.
     * Avoids MultipleBagFetchException by fetching annexes via JOIN FETCH
     * and then initializing bonuses per annex within the same EntityManager.
     */
    public CollectiveAgreement getAgreementWithDetails(Long id) {
        if (id == null) return null;
        EntityManager em = JpaUtil.getEntityManager();
        try {
            CollectiveAgreement c = em.createQuery(
                    "SELECT DISTINCT c FROM CollectiveAgreement c " +
                    "LEFT JOIN FETCH c.annexes a " +
                    "WHERE c.id = :id",
                    CollectiveAgreement.class)
                .setParameter("id", id)
                .getResultStream().findFirst().orElse(null);
            if (c != null && c.getAnnexes() != null) {
                // Initialize bonuses for each annex to avoid LazyInitialization outside EM
                for (AgreementAnnex a : c.getAnnexes()) {
                    if (a != null && a.getBonuses() != null) {
                        a.getBonuses().size();
                    }
                }
            }
            return c;
        } finally { em.close(); }
    }

    /** Find agreement active today (startDate <= today <= endDate or endDate null). */
    public CollectiveAgreement findCurrentAgreement() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            java.time.LocalDate today = java.time.LocalDate.now();
            return em.createQuery(
                    "SELECT c FROM CollectiveAgreement c " +
                    "WHERE c.startDate <= :today AND (c.endDate IS NULL OR c.endDate >= :today) " +
                    "ORDER BY c.startDate DESC",
                    CollectiveAgreement.class)
                .setParameter("today", today)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    /** Find annex active today for a given agreement. */
    public AgreementAnnex findCurrentAnnex(Long agreementId) {
        if (agreementId == null) return null;
        EntityManager em = JpaUtil.getEntityManager();
        try {
            java.time.LocalDate today = java.time.LocalDate.now();
            return em.createQuery(
                    "SELECT a FROM AgreementAnnex a " +
                    "WHERE a.agreement.id = :id AND a.startDate <= :today " +
                    "AND (a.endDate IS NULL OR a.endDate >= :today) ORDER BY a.startDate DESC",
                    AgreementAnnex.class)
                .setParameter("id", agreementId)
                .setParameter("today", today)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    // ===== Orquestación con reglas de fechas + auditoría =====

    public void createWithFirstAnnex(Administrator admin, CollectiveAgreement agreement, AgreementAnnex firstAnnex) {
        if (admin == null) throw new IllegalArgumentException("admin required");
        if (agreement == null || agreement.getStartDate() == null) throw new IllegalArgumentException("agreement with startDate required");
        if (firstAnnex == null || firstAnnex.getStartDate() == null) throw new IllegalArgumentException("first annex with startDate required");

        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Continuidad de convenios (global): si hay uno abierto, ciérralo el día anterior
            CollectiveAgreement last = em.createQuery(
                "SELECT c FROM CollectiveAgreement c WHERE c.endDate IS NULL OR c.endDate >= :sd ORDER BY c.startDate DESC", CollectiveAgreement.class)
                .setParameter("sd", agreement.getStartDate())
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);

            if (last != null) {
                if (last.getEndDate() == null) {
                    // cerrar en startDate-1
                    LocalDate closeDate = agreement.getStartDate().minusDays(1);
                    if (!closeDate.isBefore(last.getStartDate())) {
                        last.setEndDate(closeDate);
                        em.merge(last);
                    } else {
                        throw new IllegalArgumentException("New agreement starts before the previous one starts");
                    }
                } else {
                    // Debe empezar justo al día siguiente del anterior
                    if (!agreement.getStartDate().equals(last.getEndDate().plusDays(1))) {
                        throw new IllegalArgumentException("Agreement must start the day after the previous ends");
                    }
                }
            }

            agreement.setCreatedBy(admin);
            em.persist(agreement);

            // Primer anejo debe empezar el mismo día que el convenio
            if (!firstAnnex.getStartDate().equals(agreement.getStartDate())) {
                throw new IllegalArgumentException("First annex must start at agreement start date");
            }
            firstAnnex.setAgreement(agreement);
            firstAnnex.setCreatedBy(admin);
            em.persist(firstAnnex);

            // Audit
            logChange(em, admin, agreement, null, "CREATED_AGREEMENT", "Agreement created with start=" + agreement.getStartDate());
            logChange(em, admin, agreement, firstAnnex, "CREATED_ANNEX", "First annex created start=" + firstAnnex.getStartDate());

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    public void addAnnex(Administrator admin, Long agreementId, AgreementAnnex next) {
        if (admin == null) throw new IllegalArgumentException("admin required");
        if (agreementId == null || next == null || next.getStartDate() == null) throw new IllegalArgumentException("agreementId and annex with startDate required");

        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            CollectiveAgreement agreement = em.find(CollectiveAgreement.class, agreementId);
            if (agreement == null) throw new IllegalArgumentException("Agreement not found");

            // último anejo
            AgreementAnnex last = em.createQuery(
                "SELECT a FROM AgreementAnnex a WHERE a.agreement.id = :id ORDER BY a.startDate DESC", AgreementAnnex.class)
                .setParameter("id", agreementId)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);

            if (last == null) {
                // si no hay, este es el primero y debe empezar con el acuerdo
                if (!next.getStartDate().equals(agreement.getStartDate()))
                    throw new IllegalArgumentException("First annex must start at agreement start date");
            } else {
                LocalDate expected = last.getEndDate() != null ? last.getEndDate().plusDays(1) : next.getStartDate();
                if (last.getEndDate() == null) {
                    // cerrar el anterior justo el día anterior
                    last.setEndDate(next.getStartDate().minusDays(1));
                    em.merge(last);
                } else if (!next.getStartDate().equals(expected)) {
                    throw new IllegalArgumentException("Annex must start the day after the previous ends");
                }
            }

            next.setAgreement(agreement);
            next.setCreatedBy(admin);
            em.persist(next);

            logChange(em, admin, agreement, next, "CREATED_ANNEX", "Annex created start=" + next.getStartDate());
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    public void closeAgreement(Administrator admin, Long agreementId, LocalDate endDate) {
        if (admin == null) throw new IllegalArgumentException("admin required");
        if (agreementId == null || endDate == null) throw new IllegalArgumentException("agreementId and endDate required");

        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            CollectiveAgreement agreement = em.find(CollectiveAgreement.class, agreementId);
            if (agreement == null) throw new IllegalArgumentException("Agreement not found");
            if (agreement.getStartDate() != null && endDate.isBefore(agreement.getStartDate()))
                throw new IllegalArgumentException("endDate before startDate");

            // ultimo anejo
            AgreementAnnex last = em.createQuery(
                "SELECT a FROM AgreementAnnex a WHERE a.agreement.id = :id ORDER BY a.startDate DESC", AgreementAnnex.class)
                .setParameter("id", agreementId)
                .setMaxResults(1)
                .getResultStream().findFirst().orElse(null);
            if (last != null) {
                if (last.getEndDate() == null || last.getEndDate().isAfter(endDate)) {
                    last.setEndDate(endDate);
                    em.merge(last);
                }
            }

            agreement.setEndDate(endDate);
            em.merge(agreement);

            logChange(em, admin, agreement, null, "CLOSED_AGREEMENT", "Closed at " + endDate);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    private static void logChange(EntityManager em, Administrator admin, CollectiveAgreement agreement, AgreementAnnex annex, String type, String message) {
        AgreementChange ch = new AgreementChange();
        ch.setAgreement(agreement);
        ch.setAnnex(annex);
        ch.setAdministrator(admin);
        ch.setType(type);
        ch.setMessage(message);
        em.persist(ch);
    }
}
