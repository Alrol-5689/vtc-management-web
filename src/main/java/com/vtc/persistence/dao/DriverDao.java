package com.vtc.persistence.dao;

import com.vtc.model.user.Driver;

public interface DriverDao extends GenericDao<Driver, Long> {

    Driver findByUsername(String username);

    Driver findByUsernameAndPassword(String username, String password);
}
