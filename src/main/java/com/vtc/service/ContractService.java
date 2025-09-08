package com.vtc.service;

import java.util.List;

import com.vtc.model.contract.Contract;
import com.vtc.persistence.dao.ContractDao;
import com.vtc.persistence.jpa.ContractDaoJpa;

public class ContractService {

    private final ContractDao contractDao;

    public ContractService() { this.contractDao = new ContractDaoJpa(); }

    public ContractService(ContractDao contractDao) { this.contractDao = contractDao; }

    public void createContract(Contract contract) { contractDao.create(contract); }
    public void updateContract(Contract contract) { contractDao.update(contract); }
    public void createOrUpdateContract(Contract contract) { contractDao.createOrUpdate(contract); }
    public void deleteContract(Long id) { contractDao.delete(id); }

    public List<Contract> listContracts() { return contractDao.findAll(); }
    public Contract getContract(Long id) { return contractDao.findById(id); }
}

