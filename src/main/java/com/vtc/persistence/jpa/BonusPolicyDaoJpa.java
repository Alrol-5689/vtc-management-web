package com.vtc.persistence.jpa;

import com.vtc.model.contract.BonusPolicy;
import com.vtc.persistence.dao.BonusPolicyDao;
public class BonusPolicyDaoJpa extends GenericDaoJpa<BonusPolicy, Long> implements BonusPolicyDao {

    public BonusPolicyDaoJpa() { super(BonusPolicy.class); }
}
