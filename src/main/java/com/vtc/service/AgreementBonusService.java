package com.vtc.service;

import java.util.List;

import com.vtc.model.agreement.AgreementBonus;
import com.vtc.persistence.dao.AgreementBonusDao;
import com.vtc.persistence.jpa.AgreementBonusDaoJpa;

public class AgreementBonusService {

    private final AgreementBonusDao bonusDao;

    public AgreementBonusService() { this.bonusDao = new AgreementBonusDaoJpa(); }

    public AgreementBonusService(AgreementBonusDao bonusDao) { this.bonusDao = bonusDao; }

    public void createBonus(AgreementBonus bonus) { bonusDao.create(bonus); }
    public void updateBonus(AgreementBonus bonus) { bonusDao.update(bonus); }
    public void createOrUpdateBonus(AgreementBonus bonus) { bonusDao.createOrUpdate(bonus); }
    public void deleteBonus(Long id) { bonusDao.delete(id); }

    public List<AgreementBonus> listBonuses() { return bonusDao.findAll(); }
    public AgreementBonus getBonus(Long id) { return bonusDao.findById(id); }
}

