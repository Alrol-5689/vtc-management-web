package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.contract.Contract;
import com.vtc.persistence.dao.ContractDao;
import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class ContractDaoJpa implements ContractDao {

    public ContractDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(Contract contract) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(contract);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<Contract> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT c FROM Contract c", Contract.class)
                     .getResultList();
        }
    }

    @Override
    public Contract findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(Contract.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(Contract contract) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(contract);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(Contract contract) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (contract.getId() == null) em.persist(contract);
            else em.merge(contract);
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
            Contract c = em.find(Contract.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

