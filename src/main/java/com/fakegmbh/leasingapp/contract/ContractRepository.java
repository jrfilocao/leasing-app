package com.fakegmbh.leasingapp.contract;

import com.fakegmbh.leasingapp.contract.model.ContractEntity;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<ContractEntity, Long> {
}
