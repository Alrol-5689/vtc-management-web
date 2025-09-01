package com.vtc.persistence.dao;

import com.vtc.model.user.Driver;
import java.util.List;

public interface DriverDao {

    void create(Driver driver);

    List<Driver> findAll();

    Driver findById(Long id);

    Driver findByUsername(String username);

    Driver findByUsernameAndPassword(String username, String password);

    void update(Driver driver);

    void createOrUpdate(Driver driver);

    void delete(Long id);
}
