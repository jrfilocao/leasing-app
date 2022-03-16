package com.fakegmbh.leasingapp.contract;

import com.fakegmbh.leasingapp.LeasingAppApplication;
import com.fakegmbh.leasingapp.contract.model.ContractDto;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests starting a Spring application context and the server.
 * Kind of sanity check or smoke tests confirming that the most crucial functions of the domain work.
 *
 * <p>We are not using testcontainers here. So, the mysql container should be running.
 *
 * <p>Exceptionally, we have multiple asserts per test. Each test is handling the CRUD operations for one domain.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = LeasingAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractIntegrationTests {

    private static final String CONTRACT_ENDPOINT = "/api/contracts";
    private static final String CUSTOMERS_ENDPOINT = "/api/customers";
    private static final String VEHICLES_ENDPOINT = "/api/vehicles";

    private static final long CONTRACT_ID = 1L;
    private static final long VEHICLE_ID = 1L;
    private static final long VEHICLE_TYPE_ID = 3L;
    private static final String BRAND = "BMW";
    private static final String MODEL = "X3";
    private static final String VIN = "12345";
    private static final int MODEL_YEAR = 2015;
    private static final long CUSTOMER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;
    private static final String CONTRACT_NUMBER = "1234567";
    private static final BigDecimal PRICE = new BigDecimal("100.00");
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("20.00");
    private static final BigDecimal NEW_MONTHLY_RATE = new BigDecimal("40.00");

    private static final String HOST = "http://localhost:";
    private static final String SEPARATOR = "/";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Flyway flyway;
    private CustomerDto customer;
    private VehicleDto vehicle;

    @BeforeTestClass
    public void init() {
        flyway.clean();
        flyway.migrate();
    }

    private ContractDto contract;
    private ContractDto contractToBeUpdated;

    @BeforeEach
    public void setUp() {
        final VehicleTypeDto vehicleTypeDto = VehicleTypeDto.builder()
                                                            .vehicleTypeId(VEHICLE_TYPE_ID)
                                                            .brand(BRAND)
                                                            .model(MODEL)
                                                            .build();
        vehicle = VehicleDto.builder()
                            .vehicleId(VEHICLE_ID)
                            .vehicleType(vehicleTypeDto)
                            .price(PRICE)
                            .vin(VIN)
                            .modelYear(MODEL_YEAR)
                            .build();

        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        customer = CustomerDto.builder()
                              .customerId(CUSTOMER_ID)
                              .firstName(FIRST_NAME)
                              .lastName(LAST_NAME)
                              .birthdate(birthdate)
                              .build();

        contract = ContractDto.builder()
                              .contractId(CONTRACT_ID)
                              .contractNumber(CONTRACT_NUMBER)
                              .monthlyRate(MONTHLY_RATE)
                              .customer(customer)
                              .vehicle(vehicle)
                              .build();
        contractToBeUpdated = ContractDto.builder()
                              .contractId(CONTRACT_ID)
                              .contractNumber(CONTRACT_NUMBER)
                              .monthlyRate(NEW_MONTHLY_RATE)
                              .customer(customer)
                              .vehicle(vehicle)
                              .build();
    }

    @Test
    public void createRetrieveAndUpdateContract() {
        createCustomer();
        createVehicle();
        final ResponseEntity<ContractDto> createdEntity = this.restTemplate.postForEntity(HOST + port + CONTRACT_ENDPOINT,
                                                                                         contract,
                                                                                         ContractDto.class);
        assertThat(createdEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final Long contractId = Objects.requireNonNull(createdEntity.getBody()).getContractId();

        final ResponseEntity<ContractDto> retrievedEntity = this.restTemplate.getForEntity(HOST + port + CONTRACT_ENDPOINT + SEPARATOR + contractId,
                                                                                          ContractDto.class);
        assertThat(retrievedEntity.getBody()).isEqualTo(createdEntity.getBody());

        this.restTemplate.put(HOST + port + CONTRACT_ENDPOINT + SEPARATOR + contractId,
                              contractToBeUpdated,
                              ContractDto.class);

        final ResponseEntity<ContractDto> updatedEntity = this.restTemplate.getForEntity(HOST + port + CONTRACT_ENDPOINT + SEPARATOR + contractId,
                                                                                        ContractDto.class);
        assertThat(updatedEntity.getBody()).isEqualTo(contractToBeUpdated);
    }

    private void createVehicle() {
        final ResponseEntity<VehicleDto> createdVehicle = this.restTemplate.postForEntity(HOST + port + VEHICLES_ENDPOINT,
                                                                                         vehicle,
                                                                                         VehicleDto.class);
        assertThat(createdVehicle.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    private void createCustomer() {
        final ResponseEntity<CustomerDto> createdCustomer = this.restTemplate.postForEntity(HOST + port + CUSTOMERS_ENDPOINT,
                                                                                          customer,
                                                                                          CustomerDto.class);
        assertThat(createdCustomer.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @AfterAll
    public void tearDown() {
        flyway.clean();
    }
}