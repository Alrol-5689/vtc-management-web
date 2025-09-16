package com.vtc.persistence.jpa;

import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.persistence.dao.CollectiveAgreementDao;
public class CollectiveAgreementDaoJpa extends GenericDaoJpa<CollectiveAgreement, Long> implements CollectiveAgreementDao {

    public CollectiveAgreementDaoJpa() { super(CollectiveAgreement.class); }
}
