package com.vtc.service;

import java.util.List;

import com.vtc.model.contract.CommissionPolicy;
import com.vtc.persistence.dao.CommissionPolicyDao;
import com.vtc.persistence.jpa.CommissionPolicyDaoJpa;

public class CommissionPolicyService {

    private final CommissionPolicyDao policyDao;

    public CommissionPolicyService() { this.policyDao = new CommissionPolicyDaoJpa(); }

    public CommissionPolicyService(CommissionPolicyDao policyDao) { this.policyDao = policyDao; }

    public void createPolicy(CommissionPolicy policy) { policyDao.create(policy); }
    public void updatePolicy(CommissionPolicy policy) { policyDao.update(policy); }
    public void createOrUpdatePolicy(CommissionPolicy policy) { policyDao.createOrUpdate(policy); }
    public void deletePolicy(Long id) { policyDao.delete(id); }

    public List<CommissionPolicy> listPolicies() { return policyDao.findAll(); }
    public CommissionPolicy getPolicy(Long id) { return policyDao.findById(id); }
}

