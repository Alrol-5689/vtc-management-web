package com.vtc.persistence.jpa;

import com.vtc.model.contract.CommissionPolicy;
import com.vtc.persistence.dao.CommissionPolicyDao;
public class CommissionPolicyDaoJpa extends GenericDaoJpa<CommissionPolicy, Long> implements CommissionPolicyDao {

    public CommissionPolicyDaoJpa() { super(CommissionPolicy.class); }
}
