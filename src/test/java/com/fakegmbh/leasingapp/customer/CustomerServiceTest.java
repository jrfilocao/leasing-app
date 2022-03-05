package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.customer.exception.CustomerNotFoundException;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.customer.model.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private static final long CUSTOMER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private CustomerDto customerDtoWithoutId;
    private CustomerDto customerDtoWithId;
    private CustomerEntity customerEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        customerDtoWithoutId = CustomerDto.builder()
                                          .firstName(FIRST_NAME)
                                          .lastName(LAST_NAME)
                                          .birthdate(birthdate)
                                          .build();

        customerDtoWithId = CustomerDto.builder()
                                       .customerId(CUSTOMER_ID)
                                       .firstName(FIRST_NAME)
                                       .lastName(LAST_NAME)
                                       .birthdate(birthdate)
                                       .build();

        customerEntity = CustomerEntity.builder()
                                       .customerId(CUSTOMER_ID)
                                       .firstName(FIRST_NAME)
                                       .lastName(LAST_NAME)
                                       .birthdate(birthdate)
                                       .lastModified(Timestamp.from(Instant.now()))
                                       .dateCreated(Timestamp.from(Instant.now()))
                                       .build();
    }

    @Test
    public void testGetValidCustomer() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.mapTo(customerEntity)).thenReturn(customerDtoWithId);

        assertThat(customerService.getCustomer(CUSTOMER_ID)).isEqualTo(customerDtoWithId);
    }

    @Test
    public void testGetInvalidCustomer() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomer(CUSTOMER_ID)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    public void testCreateCustomer() {
        when(customerMapper.mapTo(customerDtoWithoutId)).thenReturn(customerEntity);
        when(customerMapper.mapTo(customerEntity)).thenReturn(customerDtoWithId);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);

        assertThat(customerService.createCustomer(customerDtoWithoutId)).isEqualTo(customerDtoWithId);
    }

    @Test
    public void testUpdateCustomerValidId() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.mapTo(customerEntity)).thenReturn(customerDtoWithId);

        assertThat(customerService.updateCustomer(CUSTOMER_ID, customerDtoWithoutId)).isEqualTo(customerDtoWithId);
    }

    @Test
    public void testUpdateCustomerInvalidId() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomer(CUSTOMER_ID, customerDtoWithoutId)).isInstanceOf(CustomerNotFoundException.class);
    }
}
