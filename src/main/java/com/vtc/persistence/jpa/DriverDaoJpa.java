package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.user.Driver;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.DriverDao;

import jakarta.persistence.EntityManager;

public class DriverDaoJpa implements DriverDao {

    //===>> CONSTRUCTORS <<===//

    public DriverDaoJpa() {}

    //===>> METHODS <<===//
    
    private EntityManager em() {
        return JpaUtil.getEntityManager();
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(Driver driver) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(driver);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Driver> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT d FROM Driver d", Driver.class)
                     .getResultList();
        }
    }

    @Override
    public Driver findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(Driver.class, id);
        }
    }

    @Override
    public Driver findByUsername(String username) {
        try (EntityManager em = em()) {
            List<Driver> list = em.createQuery(
                        "SELECT d FROM Driver d WHERE d.username = :u", Driver.class)
                    .setParameter("u", username)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public Driver findByUsernameAndPassword(String username, String password) {
        try (EntityManager em = em()) {
            List<Driver> list = em.createQuery(
                        "SELECT d FROM Driver d WHERE d.username = :u AND d.password = :p", Driver.class)
                    .setParameter("u", username)
                    .setParameter("p", password)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(Driver driver) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(driver);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(Driver driver) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (driver.getId() == null) em.persist(driver);
            else em.merge(driver);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void delete(Long id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            Driver d = em.find(Driver.class, id);
            if (d != null) em.remove(d);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
}
