package com.fakegmbh.leasingapp.customer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest {

    private static final long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;

    private ModelMapper modelMapper;

    private CustomerMapper customerMapper;

    private CustomerDto customerDto;
    private CustomerEntity customer;
    private CustomerEntity customerWithoutTimestamps;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        customerMapper = new CustomerMapper(modelMapper);
        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        customerDto = CustomerDto.builder()
                                 .customerId(ID)
                                 .firstName(FIRST_NAME)
                                 .lastName(LAST_NAME)
                                 .birthdate(birthdate)
                                 .build();

        customer = CustomerEntity.builder()
                                 .customerId(ID)
                                 .firstName(FIRST_NAME)
                                 .lastName(LAST_NAME)
                                 .birthdate(birthdate)
                                 .updatedAt(Timestamp.from(Instant.now()))
                                 .createdAt(Timestamp.from(Instant.now()))
                                 .build();

        customerWithoutTimestamps = CustomerEntity.builder()
                                 .customerId(ID)
                                 .firstName(FIRST_NAME)
                                 .lastName(LAST_NAME)
                                 .birthdate(birthdate)
                                 .build();
    }

    @Test
    public void testMapToCustomerDto() {
        assertThat(customerMapper.mapTo(customer)).isEqualTo(customerDto);
    }

    @Test
    public void testMapToCustomer() {
        assertThat(customerMapper.mapTo(customerDto)).isEqualTo(customerWithoutTimestamps);
    }
}

