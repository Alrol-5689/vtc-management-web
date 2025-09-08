package com.vtc.persistence.dao;

import com.vtc.model.contract.ContractAppendix;
import java.util.List;

public interface ContractAppendixDao {

    void create(ContractAppendix appendix);

    List<ContractAppendix> findAll();

    ContractAppendix findById(Long id);

    void update(ContractAppendix appendix);

    void createOrUpdate(ContractAppendix appendix);

    void delete(Long id);
}

