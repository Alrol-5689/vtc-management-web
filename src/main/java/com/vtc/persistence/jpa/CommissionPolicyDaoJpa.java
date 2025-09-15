package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.contract.CommissionPolicy;
import com.vtc.persistence.dao.CommissionPolicyDao;
import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class CommissionPolicyDaoJpa implements CommissionPolicyDao {

    public CommissionPolicyDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(CommissionPolicy policy) {
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
    public List<CommissionPolicy> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT p FROM CommissionPolicy p", CommissionPolicy.class)
                     .getResultList();
        }
    }

    @Override
    public CommissionPolicy findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(CommissionPolicy.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(CommissionPolicy policy) {
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
    public void createOrUpdate(CommissionPolicy policy) {
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
            CommissionPolicy p = em.find(CommissionPolicy.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

