package com.vtc.persistence.jpa;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.persistence.dao.AgreementAnnexDao;
public class AgreementAnnexDaoJpa extends GenericDaoJpa<AgreementAnnex, Long> implements AgreementAnnexDao {

    public AgreementAnnexDaoJpa() { super(AgreementAnnex.class); }
}
