package com.vtc.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.vtc.model.user.Driver;
import com.vtc.persistence.dao.DriverDao;
import com.vtc.persistence.jpa.DriverDaoJpa;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Application service for Driver use-cases.
 * Orchestrates business logic and delegates persistence to DriverDao.
 */
public class DriverService {

    private final DriverDao driverDao;
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

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
        if (driver == null) throw new IllegalArgumentException("Driver cannot be null");

        List<String> errors = new ArrayList<>(3);

        // Validate only targeted fields for speed
        Set<ConstraintViolation<Driver>> emailViolations = VALIDATOR.validateProperty(driver, "email");
        if (!emailViolations.isEmpty()) {
            emailViolations.forEach(v -> errors.add("email: " + v.getMessage()));
        }

        Set<ConstraintViolation<Driver>> phoneViolations = VALIDATOR.validateProperty(driver, "phone");
        if (!phoneViolations.isEmpty()) {
            phoneViolations.forEach(v -> errors.add("phone: " + v.getMessage()));
        }

        Set<ConstraintViolation<Driver>> idViolations = VALIDATOR.validateProperty(driver, "nationalId");
        if (!idViolations.isEmpty()) {
            idViolations.forEach(v -> errors.add("nationalId: " + v.getMessage()));
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", errors));
        }
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
