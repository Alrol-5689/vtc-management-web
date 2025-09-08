package com.vtc.service;

import java.util.List;

import com.vtc.model.user.Administrator;
import com.vtc.persistence.dao.AdministratorDao;
import com.vtc.persistence.jpa.AdministratorDaoJpa;

public class AdministratorService {

    private final AdministratorDao administratorDao;

    public AdministratorService() { this.administratorDao = new AdministratorDaoJpa(); }

    public AdministratorService(AdministratorDao administratorDao) { this.administratorDao = administratorDao; }

    public void createAdministrator(Administrator administrator) { administratorDao.create(administrator); }
    public void updateAdministrator(Administrator administrator) { administratorDao.update(administrator); }
    public void createOrUpdateAdministrator(Administrator administrator) { administratorDao.createOrUpdate(administrator); }
    public void deleteAdministrator(Long id) { administratorDao.delete(id); }

    public List<Administrator> listAdministrators() { return administratorDao.findAll(); }
    public Administrator getAdministrator(Long id) { return administratorDao.findById(id); }
}

