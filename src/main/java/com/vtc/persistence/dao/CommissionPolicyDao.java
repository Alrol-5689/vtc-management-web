package com.vtc.persistence.dao;

import com.vtc.model.contract.CommissionPolicy;
import java.util.List;

public interface CommissionPolicyDao {

    void create(CommissionPolicy policy);

    List<CommissionPolicy> findAll();

    CommissionPolicy findById(Long id);

    void update(CommissionPolicy policy);

    void createOrUpdate(CommissionPolicy policy);

    void delete(Long id);
}

