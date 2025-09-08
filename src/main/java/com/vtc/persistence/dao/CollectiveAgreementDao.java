package com.vtc.persistence.dao;

import com.vtc.model.agreement.CollectiveAgreement;
import java.util.List;

public interface CollectiveAgreementDao {

    void create(CollectiveAgreement agreement);

    List<CollectiveAgreement> findAll();

    CollectiveAgreement findById(Long id);

    void update(CollectiveAgreement agreement);

    void createOrUpdate(CollectiveAgreement agreement);

    void delete(Long id);
}

