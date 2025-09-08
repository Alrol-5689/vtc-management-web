package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.contract.ContractAppendix;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.ContractAppendixDao;

import jakarta.persistence.EntityManager;

public class ContractAppendixDaoJpa implements ContractAppendixDao {

    public ContractAppendixDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(ContractAppendix appendix) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(appendix);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<ContractAppendix> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT a FROM ContractAppendix a", ContractAppendix.class)
                     .getResultList();
        }
    }

    @Override
    public ContractAppendix findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(ContractAppendix.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(ContractAppendix appendix) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(appendix);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(ContractAppendix appendix) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (appendix.getId() == null) em.persist(appendix);
            else em.merge(appendix);
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
            ContractAppendix a = em.find(ContractAppendix.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

