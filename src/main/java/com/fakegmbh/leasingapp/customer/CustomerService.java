package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.customer.exception.CustomerNotFoundException;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.customer.model.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto getCustomer(final Long customerId) {
        final CustomerEntity customerEntity = customerRepository.findById(customerId)
                                                                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customerMapper.mapTo(customerEntity);
    }

    @Transactional
    public CustomerDto updateCustomer(final Long customerId, final CustomerDto customerToBeUpdated) {
        final CustomerEntity customerEntity = customerRepository.findById(customerId)
                                                                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerToBeUpdated.setCustomerId(customerId);
        final CustomerEntity updatedCustomerEntity = customerRepository.save(customerEntity);
        return customerMapper.mapTo(updatedCustomerEntity);
    }

    @Transactional
    public CustomerDto createCustomer(final CustomerDto customerDto) {
        final CustomerEntity customerEntity = customerMapper.mapTo(customerDto);
        final CustomerEntity persistedCustomerEntity = customerRepository.save(customerEntity);
        return customerMapper.mapTo(persistedCustomerEntity);
    }
}
