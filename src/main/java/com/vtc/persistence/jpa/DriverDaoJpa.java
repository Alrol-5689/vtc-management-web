package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.user.Driver;
import com.vtc.persistence.dao.DriverDao;
import jakarta.persistence.EntityManager;

public class DriverDaoJpa extends GenericDaoJpa<Driver, Long> implements DriverDao {

    public DriverDaoJpa() { super(Driver.class); }

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
}
