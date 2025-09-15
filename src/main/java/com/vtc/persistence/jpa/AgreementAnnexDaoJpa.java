package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.persistence.dao.AgreementAnnexDao;
import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class AgreementAnnexDaoJpa implements AgreementAnnexDao {

    public AgreementAnnexDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(AgreementAnnex annex) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(annex);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<AgreementAnnex> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT a FROM AgreementAnnex a", AgreementAnnex.class)
                     .getResultList();
        }
    }

    @Override
    public AgreementAnnex findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(AgreementAnnex.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(AgreementAnnex annex) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(annex);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(AgreementAnnex annex) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (annex.getId() == null) em.persist(annex);
            else em.merge(annex);
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
            AgreementAnnex a = em.find(AgreementAnnex.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

