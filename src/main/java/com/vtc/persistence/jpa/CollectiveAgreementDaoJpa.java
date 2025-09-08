package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.CollectiveAgreementDao;

import jakarta.persistence.EntityManager;

public class CollectiveAgreementDaoJpa implements CollectiveAgreementDao {

    public CollectiveAgreementDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(CollectiveAgreement agreement) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(agreement);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<CollectiveAgreement> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT a FROM CollectiveAgreement a", CollectiveAgreement.class)
                     .getResultList();
        }
    }

    @Override
    public CollectiveAgreement findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(CollectiveAgreement.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(CollectiveAgreement agreement) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(agreement);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(CollectiveAgreement agreement) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (agreement.getId() == null) em.persist(agreement);
            else em.merge(agreement);
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
            CollectiveAgreement a = em.find(CollectiveAgreement.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

