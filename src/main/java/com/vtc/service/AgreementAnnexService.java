package com.vtc.service;

import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.persistence.dao.AgreementAnnexDao;
import com.vtc.persistence.jpa.AgreementAnnexDaoJpa;

public class AgreementAnnexService {

    private final AgreementAnnexDao annexDao;

    public AgreementAnnexService() { this.annexDao = new AgreementAnnexDaoJpa(); }

    public AgreementAnnexService(AgreementAnnexDao annexDao) { this.annexDao = annexDao; }

    public void createAnnex(AgreementAnnex annex) { annexDao.create(annex); }
    public void updateAnnex(AgreementAnnex annex) { annexDao.update(annex); }
    public void createOrUpdateAnnex(AgreementAnnex annex) { annexDao.createOrUpdate(annex); }
    public void deleteAnnex(Long id) { annexDao.delete(id); }

    public List<AgreementAnnex> listAnnexes() { return annexDao.findAll(); }
    public AgreementAnnex getAnnex(Long id) { return annexDao.findById(id); }
}

