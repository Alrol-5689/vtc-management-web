package com.vtc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vtc.model.user.Driver;
import com.vtc.persistence.dao.DriverDao;
import com.vtc.persistence.jpa.DriverDaoJpa;
import org.mindrot.jbcrypt.BCrypt;

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
        if (driver == null) throw new IllegalArgumentException("Driver cannot be null");
        if (driver.getId() == null) throw new IllegalArgumentException("Driver id is required for update");

        Driver existing = driverDao.findById(driver.getId());
        if (existing == null) throw new IllegalArgumentException("Driver not found: id=" + driver.getId());

        List<String> errors = new ArrayList<>(4);

        // Validate targeted fields for speed (same as create)
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

        // Ensure username uniqueness if changed
        String newUsername = driver.getUsername();
        if (newUsername != null && !newUsername.equals(existing.getUsername())) {
            Driver byUsername = driverDao.findByUsername(newUsername);
            if (byUsername != null && !byUsername.getId().equals(driver.getId())) {
                errors.add("username: already in use");
            }
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", errors));
        }
        driverDao.update(driver);
    }

    public void createOrUpdateDriver(Driver driver) {
        if (driver == null) throw new IllegalArgumentException("Driver cannot be null");

        List<String> errors = new ArrayList<>(4);

        if (driver.getId() == null) {
            // Creation path: validate targeted fields (same as createDriver)
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
        } else {
            // Update path: ensure exists and validate targeted fields + username changes
            Driver existing = driverDao.findById(driver.getId());
            if (existing == null) throw new IllegalArgumentException("Driver not found: id=" + driver.getId());

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

            String newUsername = driver.getUsername();
            if (newUsername != null && !newUsername.equals(existing.getUsername())) {
                Driver byUsername = driverDao.findByUsername(newUsername);
                if (byUsername != null && !byUsername.getId().equals(driver.getId())) {
                    errors.add("username: already in use");
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", errors));
        }

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
        if (username == null || password == null) return null;
        Driver d = driverDao.findByUsername(username);
        if (d == null) return null;
        String stored = d.getPassword();
        if (stored == null) return null;
        try {
            // Prefer BCrypt when hash present; fallback to plain equals for legacy rows
            boolean isBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");
            boolean ok = isBcrypt ? BCrypt.checkpw(password, stored) : password.equals(stored);
            return ok ? d : null;
        } catch (IllegalArgumentException ex) {
            // In case stored is not a valid BCrypt string
            return null;
        }
    }
}
