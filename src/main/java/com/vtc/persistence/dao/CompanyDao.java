package com.vtc.persistence.dao;

import com.vtc.model.company.Company;
import java.util.List;

public interface CompanyDao extends GenericDao<Company, Long> {
    List<Company> findByDriverId(Long driverId);
}
