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

    public Administrator findByUsername(String username) { return administratorDao.findByUsername(username); }

    public Administrator authenticate(String username, String password) {
        if (username == null || password == null) return null;
        Administrator a = administratorDao.findByUsername(username);
        if (a == null) return null;
        String stored = a.getPassword();
        if (stored == null) return null;
        try {
            boolean isBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");
            boolean ok = isBcrypt ? org.mindrot.jbcrypt.BCrypt.checkpw(password, stored) : password.equals(stored);
            return ok ? a : null;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
