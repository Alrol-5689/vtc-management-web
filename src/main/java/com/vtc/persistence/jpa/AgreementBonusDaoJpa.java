package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.AgreementBonus;
import com.vtc.persistence.dao.AgreementBonusDao;
import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class AgreementBonusDaoJpa implements AgreementBonusDao {

    public AgreementBonusDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(AgreementBonus bonus) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (bonus.getAnnex() != null && bonus.getAnnex().getId() != null) {
                bonus.setAnnex(em.getReference(AgreementAnnex.class, bonus.getAnnex().getId()));
            }
            em.persist(bonus);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<AgreementBonus> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT b FROM AgreementBonus b", AgreementBonus.class)
                     .getResultList();
        }
    }

    @Override
    public AgreementBonus findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(AgreementBonus.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(AgreementBonus bonus) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(bonus);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(AgreementBonus bonus) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (bonus.getAnnex() != null && bonus.getAnnex().getId() != null) {
                bonus.setAnnex(em.getReference(AgreementAnnex.class, bonus.getAnnex().getId()));
            }
            if (bonus.getId() == null) em.persist(bonus);
            else em.merge(bonus);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void delete(Long id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            AgreementBonus b = em.find(AgreementBonus.class, id);
            if (b != null) em.remove(b);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}
