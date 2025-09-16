package com.vtc.persistence.jpa;

import java.util.List;

import com.vtc.model.payslip.Payslip;
import com.vtc.persistence.dao.PayslipDao;
import jakarta.persistence.EntityManager;

public class PayslipDaoJpa extends GenericDaoJpa<Payslip, Long> implements PayslipDao {

    public PayslipDaoJpa() { super(Payslip.class); }

    @Override
    public List<Payslip> findByDriverAndCompany(Long driverId, Long companyId) {
        try (EntityManager em = em()) {
            return em.createQuery(
                            "SELECT p FROM Payslip p WHERE p.contract.driver.id = :did AND p.contract.company.id = :cid ORDER BY p.month DESC",
                            Payslip.class)
                    .setParameter("did", driverId)
                    .setParameter("cid", companyId)
                    .getResultList();
        }
    }
}
