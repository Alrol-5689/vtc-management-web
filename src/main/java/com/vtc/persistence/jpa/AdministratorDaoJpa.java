package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.user.Administrator;
import com.vtc.persistence.dao.AdministratorDao;
import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class AdministratorDaoJpa implements AdministratorDao {

    public AdministratorDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(Administrator administrator) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(administrator);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<Administrator> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT a FROM Administrator a", Administrator.class)
                     .getResultList();
        }
    }

    @Override
    public Administrator findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(Administrator.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(Administrator administrator) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(administrator);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(Administrator administrator) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (administrator.getId() == null) em.persist(administrator);
            else em.merge(administrator);
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
            Administrator a = em.find(Administrator.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public Administrator findByUsername(String username) {
        try (EntityManager em = em()) {
            List<Administrator> list = em.createQuery(
                    "SELECT a FROM Administrator a WHERE a.name = :u", Administrator.class)
                .setParameter("u", username)
                .setMaxResults(1)
                .getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public Administrator findByUsernameAndPassword(String username, String password) {
        try (EntityManager em = em()) {
            List<Administrator> list = em.createQuery(
                    "SELECT a FROM Administrator a WHERE a.name = :u AND a.password = :p", Administrator.class)
                .setParameter("u", username)
                .setParameter("p", password)
                .setMaxResults(1)
                .getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }
}
