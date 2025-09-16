package com.vtc.persistence.jpa;

import com.vtc.model.contract.Contract;
import com.vtc.persistence.dao.ContractDao;
public class ContractDaoJpa extends GenericDaoJpa<Contract, Long> implements ContractDao {

    public ContractDaoJpa() { super(Contract.class); }
}
