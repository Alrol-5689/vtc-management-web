package com.vtc.persistence.dao;

import com.vtc.model.contract.Contract;
import java.util.List;

public interface ContractDao {

    void create(Contract contract);

    List<Contract> findAll();

    Contract findById(Long id);

    void update(Contract contract);

    void createOrUpdate(Contract contract);

    void delete(Long id);
}

