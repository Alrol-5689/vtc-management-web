package com.vtc.persistence.dao;

import com.vtc.model.agreement.AgreementBonus;
import java.util.List;

public interface AgreementBonusDao {

    void create(AgreementBonus bonus);

    List<AgreementBonus> findAll();

    AgreementBonus findById(Long id);

    void update(AgreementBonus bonus);

    void createOrUpdate(AgreementBonus bonus);

    void delete(Long id);
}

