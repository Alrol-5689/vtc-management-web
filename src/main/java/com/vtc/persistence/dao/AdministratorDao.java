package com.vtc.persistence.dao;

import com.vtc.model.user.Administrator;

public interface AdministratorDao extends GenericDao<Administrator, Long> {

    Administrator findByUsername(String username);

    Administrator findByUsernameAndPassword(String username, String password);
}
