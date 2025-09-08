package com.vtc.persistence.dao;

import com.vtc.model.agreement.AgreementAnnex;
import java.util.List;

public interface AgreementAnnexDao {

    void create(AgreementAnnex annex);

    List<AgreementAnnex> findAll();

    AgreementAnnex findById(Long id);

    void update(AgreementAnnex annex);

    void createOrUpdate(AgreementAnnex annex);

    void delete(Long id);
}

