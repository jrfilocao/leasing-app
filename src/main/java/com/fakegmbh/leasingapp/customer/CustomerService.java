package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.customer.exception.CustomerNotFoundException;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.customer.model.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerService {

    private static final boolean NOT_PARALLEL = false;
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
        customerToBeUpdated.setCustomerId(customerEntity.getCustomerId());
        final CustomerEntity customerEntityToBeUpdated = customerMapper.mapTo(customerToBeUpdated);
        final CustomerEntity updatedCustomerEntity = customerRepository.save(customerEntityToBeUpdated);
        return customerMapper.mapTo(updatedCustomerEntity);
    }

    @Transactional
    public CustomerDto createCustomer(final CustomerDto customerDto) {
        final CustomerEntity customerEntity = customerMapper.mapTo(customerDto);
        final CustomerEntity persistedCustomerEntity = customerRepository.save(customerEntity);
        return customerMapper.mapTo(persistedCustomerEntity);
    }

    public List<CustomerDto> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), NOT_PARALLEL)
                            .map(customerMapper::mapTo)
                            .collect(Collectors.toList());
    }
}
