package com.vtc.persistence.dao;

import com.vtc.model.company.Company;
import java.util.List;

public interface CompanyDao {
    void create(Company company);
    List<Company> findAll();
    Company findById(Long id);
    void update(Company company);
    void createOrUpdate(Company company);
    void delete(Long id);

    List<Company> findByDriverId(Long driverId);
}

