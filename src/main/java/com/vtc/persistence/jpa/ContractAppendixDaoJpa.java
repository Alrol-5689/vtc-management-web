package com.vtc.persistence.jpa;

import com.vtc.model.contract.ContractAppendix;
import com.vtc.persistence.dao.ContractAppendixDao;
public class ContractAppendixDaoJpa extends GenericDaoJpa<ContractAppendix, Long> implements ContractAppendixDao {

    public ContractAppendixDaoJpa() { super(ContractAppendix.class); }
}
