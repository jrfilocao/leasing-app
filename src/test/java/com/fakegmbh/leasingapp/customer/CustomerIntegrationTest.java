package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.LeasingAppApplication;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = LeasingAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    private static final String CUSTOMERS_ENDPOINT = "/api/customers";
    private static final long CUSTOMER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;
    private static final String HOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Flyway flyway;

    @BeforeTestClass
    public void init(){
        flyway.clean();
        flyway.migrate();
    }

    private CustomerDto customer;

    @BeforeEach
    public void setUp() {
        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        customer = CustomerDto.builder()
                              .customerId(CUSTOMER_ID)
                              .firstName(FIRST_NAME)
                              .lastName(LAST_NAME)
                              .birthdate(birthdate)
                              .build();
    }

    @Test
    public void testAddCustomer() {
        final ResponseEntity<CustomerDto> responseEntity = this.restTemplate.postForEntity(HOST + port + CUSTOMERS_ENDPOINT,
                                                                                           customer,
                                                                                           CustomerDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @AfterAll
    public void tearDown() {
        flyway.clean();
    }
}