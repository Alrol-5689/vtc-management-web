package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.company.Company;
import com.vtc.persistence.dao.CompanyDao;
import jakarta.persistence.EntityManager;

public class CompanyDaoJpa extends GenericDaoJpa<Company, Long> implements CompanyDao {

    public CompanyDaoJpa() { super(Company.class); }

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
