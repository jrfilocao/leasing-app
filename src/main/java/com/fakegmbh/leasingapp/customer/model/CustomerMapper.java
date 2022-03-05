package com.fakegmbh.leasingapp.customer.model;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public CustomerEntity mapTo(final CustomerDto customerDto) {
        return modelMapper.map(customerDto, CustomerEntity.class);
    }

    public CustomerDto mapTo(final CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, CustomerDto.class);
    }
}
