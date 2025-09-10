package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.company.Company;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.CompanyDao;

import jakarta.persistence.EntityManager;

public class CompanyDaoJpa implements CompanyDao {

    public CompanyDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    public void create(Company company) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(company);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<Company> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT c FROM Company c", Company.class).getResultList();
        }
    }

    @Override
    public Company findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(Company.class, id);
        }
    }

    @Override
    public void update(Company company) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(company);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public void createOrUpdate(Company company) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (company.getId() == null) em.persist(company);
            else em.merge(company);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            Company c = em.find(Company.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<Company> findByDriverId(Long driverId) {
        try (EntityManager em = em()) {
            return em.createQuery(
                "SELECT DISTINCT c FROM Company c JOIN Contract ct ON ct.company = c WHERE ct.driver.id = :did",
                Company.class)
                .setParameter("did", driverId)
                .getResultList();
        }
    }
}

