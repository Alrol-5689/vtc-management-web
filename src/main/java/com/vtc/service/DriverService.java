package com.vtc.service;

import java.util.List;

import com.vtc.model.user.Driver;
import com.vtc.persistence.dao.DriverDao;
import com.vtc.persistence.jpa.DriverDaoJpa;

/**
 * Application service for Driver use-cases.
 * Orchestrates business logic and delegates persistence to DriverDao.
 */
public class DriverService {

    private final DriverDao driverDao;

    /** Default constructor using JPA implementation. */
    public DriverService() {
        this.driverDao = new DriverDaoJpa();
    }

    /** Constructor for dependency injection / testing. */
    public DriverService(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    // ====== Commands ======

    public void createDriver(Driver driver) {
        // TODO: add validations (email format, phone, nationalId) before persisting
        driverDao.create(driver);
    }

    public void updateDriver(Driver driver) {
        // TODO: validations/rules before updating
        driverDao.update(driver);
    }

    public void createOrUpdateDriver(Driver driver) {
        // Useful when ID may be null (new) or not (update)
        driverDao.createOrUpdate(driver);
    }

    public void deleteDriver(Long id) {
        driverDao.delete(id);
    }

    // ====== Queries ======

    public List<Driver> listDrivers() {
        return driverDao.findAll();
    }

    public Driver getDriver(Long id) {
        return driverDao.findById(id);
    }

    public Driver findByUsername(String username) {
        return driverDao.findByUsername(username);
    }

    public Driver authenticate(String username, String password) {
        // NOTE: For production, do not store plain passwords; use salted hashes.
        return driverDao.findByUsernameAndPassword(username, password);
    }
}
