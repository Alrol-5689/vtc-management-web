package com.vtc.service;

import java.util.List;

import com.vtc.model.contract.ContractAppendix;
import com.vtc.persistence.dao.ContractAppendixDao;
import com.vtc.persistence.jpa.ContractAppendixDaoJpa;

public class ContractAppendixService {

    private final ContractAppendixDao appendixDao;

    public ContractAppendixService() { this.appendixDao = new ContractAppendixDaoJpa(); }

    public ContractAppendixService(ContractAppendixDao appendixDao) { this.appendixDao = appendixDao; }

    public void createAppendix(ContractAppendix appendix) { appendixDao.create(appendix); }
    public void updateAppendix(ContractAppendix appendix) { appendixDao.update(appendix); }
    public void createOrUpdateAppendix(ContractAppendix appendix) { appendixDao.createOrUpdate(appendix); }
    public void deleteAppendix(Long id) { appendixDao.delete(id); }

    public List<ContractAppendix> listAppendices() { return appendixDao.findAll(); }
    public ContractAppendix getAppendix(Long id) { return appendixDao.findById(id); }
}

