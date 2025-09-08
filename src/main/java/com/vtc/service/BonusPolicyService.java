package com.vtc.service;

import java.util.List;

import com.vtc.model.contract.BonusPolicy;
import com.vtc.persistence.dao.BonusPolicyDao;
import com.vtc.persistence.jpa.BonusPolicyDaoJpa;

public class BonusPolicyService {

    private final BonusPolicyDao policyDao;

    public BonusPolicyService() { this.policyDao = new BonusPolicyDaoJpa(); }

    public BonusPolicyService(BonusPolicyDao policyDao) { this.policyDao = policyDao; }

    public void createPolicy(BonusPolicy policy) { policyDao.create(policy); }
    public void updatePolicy(BonusPolicy policy) { policyDao.update(policy); }
    public void createOrUpdatePolicy(BonusPolicy policy) { policyDao.createOrUpdate(policy); }
    public void deletePolicy(Long id) { policyDao.delete(id); }

    public List<BonusPolicy> listPolicies() { return policyDao.findAll(); }
    public BonusPolicy getPolicy(Long id) { return policyDao.findById(id); }
}

