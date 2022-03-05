package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private static final String CUSTOMERS_ENDPOINT = "/api/customers";
    private static final String PATH_ID = "/{customerId}";
    private static final long CUSTOMER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerDto customer;
    private CustomerDto customerWithoutFirstName;
    private CustomerDto customerWithoutLastName;
    private CustomerDto customerWithoutBirthdate;

    @BeforeEach
    public void setUp() {
        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);

        customerWithoutFirstName = CustomerDto.builder()
                                              .customerId(CUSTOMER_ID)
                                              .lastName(LAST_NAME)
                                              .birthdate(birthdate)
                                              .build();

        customerWithoutLastName = CustomerDto.builder()
                                             .customerId(CUSTOMER_ID)
                                             .firstName(FIRST_NAME)
                                             .birthdate(birthdate)
                                             .build();

        customerWithoutBirthdate = CustomerDto.builder()
                                              .customerId(CUSTOMER_ID)
                                              .firstName(FIRST_NAME)
                                              .lastName(LAST_NAME)
                                              .build();
        customer = CustomerDto.builder()
                              .customerId(CUSTOMER_ID)
                              .firstName(FIRST_NAME)
                              .lastName(LAST_NAME)
                              .birthdate(birthdate)
                              .build();
    }

    @Test
    public void whenValidIdThenGetCustomer() throws Exception {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(customer);

        this.mockMvc.perform(get(CUSTOMERS_ENDPOINT + PATH_ID, CUSTOMER_ID))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(customer)));
    }

    @Test
    public void whenValidCustomerThenCreateHim() throws Exception {
        when(customerService.createCustomer(customer)).thenReturn(customer);

        this.mockMvc.perform(
                post(CUSTOMERS_ENDPOINT)
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(asJsonString(customer)));
    }

    @Test
    public void whenNoFirstNameThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(CUSTOMERS_ENDPOINT)
                        .content(asJsonString(customerWithoutFirstName))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoLastNameThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(CUSTOMERS_ENDPOINT)
                        .content(asJsonString(customerWithoutLastName))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoBirthdateThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(CUSTOMERS_ENDPOINT)
                        .content(asJsonString(customerWithoutBirthdate))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(CUSTOMER_ID, customer)).thenReturn(customer);

        this.mockMvc.perform(
                put(CUSTOMERS_ENDPOINT + PATH_ID, CUSTOMER_ID)
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(customer)));
    }

    private String asJsonString(final Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}
