package com.vtc.service;

import java.util.List;

import com.vtc.model.company.Company;
import com.vtc.persistence.dao.CompanyDao;
import com.vtc.persistence.jpa.CompanyDaoJpa;

public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService() { this.companyDao = new CompanyDaoJpa(); }
    public CompanyService(CompanyDao companyDao) { this.companyDao = companyDao; }

    public void createCompany(Company company) { companyDao.create(company); }
    public void updateCompany(Company company) { companyDao.update(company); }
    public void createOrUpdateCompany(Company company) { companyDao.createOrUpdate(company); }
    public void deleteCompany(Long id) { companyDao.delete(id); }

    public List<Company> listCompanies() { return companyDao.findAll(); }
    public Company getCompany(Long id) { return companyDao.findById(id); }
    public List<Company> findByDriverId(Long driverId) { return companyDao.findByDriverId(driverId); }
}

