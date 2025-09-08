package com.vtc.service;

import java.util.List;

import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.persistence.dao.CollectiveAgreementDao;
import com.vtc.persistence.jpa.CollectiveAgreementDaoJpa;

public class CollectiveAgreementService {

    private final CollectiveAgreementDao agreementDao;

    public CollectiveAgreementService() { this.agreementDao = new CollectiveAgreementDaoJpa(); }

    public CollectiveAgreementService(CollectiveAgreementDao agreementDao) { this.agreementDao = agreementDao; }

    public void createAgreement(CollectiveAgreement agreement) { agreementDao.create(agreement); }
    public void updateAgreement(CollectiveAgreement agreement) { agreementDao.update(agreement); }
    public void createOrUpdateAgreement(CollectiveAgreement agreement) { agreementDao.createOrUpdate(agreement); }
    public void deleteAgreement(Long id) { agreementDao.delete(id); }

    public List<CollectiveAgreement> listAgreements() { return agreementDao.findAll(); }
    public CollectiveAgreement getAgreement(Long id) { return agreementDao.findById(id); }
}

