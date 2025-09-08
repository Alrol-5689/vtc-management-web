package com.vtc.persistence.dao;

import com.vtc.model.contract.BonusPolicy;
import java.util.List;

public interface BonusPolicyDao {

    void create(BonusPolicy policy);

    List<BonusPolicy> findAll();

    BonusPolicy findById(Long id);

    void update(BonusPolicy policy);

    void createOrUpdate(BonusPolicy policy);

    void delete(Long id);
}

