package com.fakegmbh.leasingapp.customer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest {

    private static final long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerMapper customerMapper;

    private CustomerDto customerDto;
    private CustomerEntity customer;

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    public void testMapToCustomerDto() {
        when(modelMapper.map(customer, CustomerDto.class)).thenReturn(customerDto);
        assertThat(customerMapper.mapTo(customer)).isEqualTo(customerDto);
    }

    @Test
    public void testMapToCustomer() {
        when(modelMapper.map(customerDto, CustomerEntity.class)).thenReturn(customer);
        assertThat(customerMapper.mapTo(customerDto)).isEqualTo(customer);
    }
}

