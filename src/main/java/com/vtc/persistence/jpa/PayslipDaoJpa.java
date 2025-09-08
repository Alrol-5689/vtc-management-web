package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.payslip.Payslip;
import com.vtc.persistence.JpaUtil;
import com.vtc.persistence.dao.PayslipDao;

import jakarta.persistence.EntityManager;

public class PayslipDaoJpa implements PayslipDao {

    public PayslipDaoJpa() {}

    private EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void create(Payslip payslip) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(payslip);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<Payslip> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("SELECT p FROM Payslip p", Payslip.class)
                     .getResultList();
        }
    }

    @Override
    public Payslip findById(Long id) {
        try (EntityManager em = em()) {
            return em.find(Payslip.class, id);
        }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void update(Payslip payslip) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(payslip);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void createOrUpdate(Payslip payslip) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (payslip.getId() == null) em.persist(payslip);
            else em.merge(payslip);
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
            Payslip p = em.find(Payslip.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}

