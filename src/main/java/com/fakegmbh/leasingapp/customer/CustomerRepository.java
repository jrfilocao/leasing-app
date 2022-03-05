package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
}
