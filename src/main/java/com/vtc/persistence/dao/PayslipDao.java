package com.vtc.persistence.dao;

import com.vtc.model.payslip.Payslip;
import java.util.List;

public interface PayslipDao {

    void create(Payslip payslip);

    List<Payslip> findAll();

    Payslip findById(Long id);

    void update(Payslip payslip);

    void createOrUpdate(Payslip payslip);

    void delete(Long id);
}

