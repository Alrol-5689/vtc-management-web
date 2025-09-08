package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.log.DailyLog;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.DailyLogDao;

import jakarta.persistence.EntityManager;

public class DailyLogDaoJpa implements DailyLogDao {

    public DailyLogDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(DailyLog log) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<DailyLog> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT l FROM DailyLog l", DailyLog.class)
                     .getResultList();
        }
    }

    @Override
    public DailyLog findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(DailyLog.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(DailyLog log) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(log);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(DailyLog log) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (log.getId() == null) em.persist(log);
            else em.merge(log);
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
            DailyLog l = em.find(DailyLog.class, id);
            if (l != null) em.remove(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

