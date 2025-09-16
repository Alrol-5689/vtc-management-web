package com.vtc.persistence.dao;

import com.vtc.model.payslip.Payslip;
import java.util.List;

public interface PayslipDao extends GenericDao<Payslip, Long> {

    List<Payslip> findByDriverAndCompany(Long driverId, Long companyId);
}
