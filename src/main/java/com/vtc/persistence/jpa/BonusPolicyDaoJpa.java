package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.contract.BonusPolicy;
import com.vtc.persistence.dao.BonusPolicyDao;
import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class BonusPolicyDaoJpa implements BonusPolicyDao {

    public BonusPolicyDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(BonusPolicy policy) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(policy);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<BonusPolicy> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT p FROM BonusPolicy p", BonusPolicy.class)
                     .getResultList();
        }
    }

    @Override
    public BonusPolicy findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(BonusPolicy.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(BonusPolicy policy) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(policy);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(BonusPolicy policy) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (policy.getId() == null) em.persist(policy);
            else em.merge(policy);
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
            BonusPolicy p = em.find(BonusPolicy.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

