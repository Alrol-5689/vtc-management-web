package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.user.Administrator;
import com.vtc.persistence.dao.AdministratorDao;

import jakarta.persistence.EntityManager;

public class AdministratorDaoJpa extends GenericDaoJpa<Administrator, Long> implements AdministratorDao {

    public AdministratorDaoJpa() { super(Administrator.class); }

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
