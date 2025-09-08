package com.vtc.persistence.dao;

import java.util.List;

import com.vtc.model.user.Administrator;

public interface AdministratorDao {

    void create(Administrator administrator);

    List<Administrator> findAll();

    Administrator findById(Long id);

    Administrator findByUsername(String username);

    Administrator findByUsernameAndPassword(String username, String password);

    void update(Administrator administrator);

    void createOrUpdate(Administrator administrator);

    void delete(Long id);
}

