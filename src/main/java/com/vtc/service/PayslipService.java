package com.vtc.service;

import java.util.List;

import com.vtc.model.payslip.Payslip;
import com.vtc.persistence.dao.PayslipDao;
import com.vtc.persistence.jpa.PayslipDaoJpa;

public class PayslipService {

    private final PayslipDao payslipDao;

    public PayslipService() { this.payslipDao = new PayslipDaoJpa(); }

    public PayslipService(PayslipDao payslipDao) { this.payslipDao = payslipDao; }

    public void createPayslip(Payslip payslip) { payslipDao.create(payslip); }
    public void updatePayslip(Payslip payslip) { payslipDao.update(payslip); }
    public void createOrUpdatePayslip(Payslip payslip) { payslipDao.createOrUpdate(payslip); }
    public void deletePayslip(Long id) { payslipDao.delete(id); }

    public List<Payslip> listPayslips() { return payslipDao.findAll(); }
    public Payslip getPayslip(Long id) { return payslipDao.findById(id); }
}

