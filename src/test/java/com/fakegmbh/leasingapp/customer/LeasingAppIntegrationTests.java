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
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests starting a Spring application context and the server.
 * Kind of sanity check or smoke tests confirming that the most crucial functions work.
 *
 * <p>We are not using testcontainers here. So, the mysql container should be running.
 *
 * <p>Exceptionally, we have multiple asserts per test. Each test is handling the CRUD operations for one domain.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = LeasingAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LeasingAppIntegrationTests {

    private static final String CUSTOMERS_ENDPOINT = "/api/customers";
    private static final long CUSTOMER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String UPDATED_FIRST_NAME = "Joe";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;
    private static final String HOST = "http://localhost:";
    protected static final String SEPARATOR = "/";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Flyway flyway;

    @BeforeTestClass
    public void init() {
        flyway.clean();
        flyway.migrate();
    }

    private CustomerDto customer;
    private CustomerDto customerToBeUpdated;

    @BeforeEach
    public void setUp() {
        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        customer = CustomerDto.builder()
                              .customerId(CUSTOMER_ID)
                              .firstName(FIRST_NAME)
                              .lastName(LAST_NAME)
                              .birthdate(birthdate)
                              .build();
        customerToBeUpdated = CustomerDto.builder()
                                         .customerId(CUSTOMER_ID)
                                         .firstName(UPDATED_FIRST_NAME)
                                         .lastName(LAST_NAME)
                                         .birthdate(birthdate)
                                         .build();
    }

    @Test
    public void createRetrieveAndUpdateCustomer() {
        final ResponseEntity<CustomerDto> createdEntity = this.restTemplate.postForEntity(HOST + port + CUSTOMERS_ENDPOINT,
                                                                                          customer,
                                                                                          CustomerDto.class);
        assertThat(createdEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final Long customerId = Objects.requireNonNull(createdEntity.getBody()).getCustomerId();

        final ResponseEntity<CustomerDto> retrievedEntity = this.restTemplate.getForEntity(HOST + port + CUSTOMERS_ENDPOINT + SEPARATOR + customerId,
                                                                                           CustomerDto.class);
        assertThat(retrievedEntity.getBody()).isEqualTo(createdEntity.getBody());

        this.restTemplate.put(HOST + port + CUSTOMERS_ENDPOINT + SEPARATOR + customerId,
                              customerToBeUpdated,
                              CustomerDto.class);

        final ResponseEntity<CustomerDto> updatedEntity = this.restTemplate.getForEntity(HOST + port + CUSTOMERS_ENDPOINT + SEPARATOR + customerId,
                                                                                         CustomerDto.class);
        assertThat(updatedEntity.getBody()).isEqualTo(customerToBeUpdated);
    }

    @AfterAll
    public void tearDown() {
        flyway.clean();
    }
}