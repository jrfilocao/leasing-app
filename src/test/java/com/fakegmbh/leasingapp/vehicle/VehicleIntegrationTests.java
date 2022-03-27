package com.fakegmbh.leasingapp.vehicle;

import com.fakegmbh.leasingapp.BaseIntegrationTest;
import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests starting a Spring application context and the server.
 * Kind of sanity check or smoke tests confirming that the most crucial functions of the domain work.
 *
 * <p>Exceptionally, we have multiple asserts per test. Each test is handling the CRUD operations for one domain.
 */
public class VehicleIntegrationTests extends BaseIntegrationTest {

    private static final String VEHICLES_ENDPOINT = "/api/vehicles";

    private static final long VEHICLE_ID = 1L;
    private static final long VEHICLE_TYPE_ID = 1L;
    private static final String BRAND = "BMW";
    private static final String MODEL = "X1";
    private static final String VIN = "12345";
    private static final int MODEL_YEAR = 2015;
    private static final int NEW_MODEL_YEAR = 2020;

    private static final String HOST = "http://localhost:";
    protected static final String SEPARATOR = "/";
    private static final BigDecimal TEN = new BigDecimal("10.00");

    @LocalServerPort
    private int port;

    private VehicleDto vehicle;
    private VehicleDto vehicleToBeUpdated;

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
                            .price(TEN)
                            .vin(VIN)
                            .modelYear(MODEL_YEAR)
                            .build();
        vehicleToBeUpdated = VehicleDto.builder()
                                       .vehicleId(VEHICLE_ID)
                                       .vehicleType(vehicleTypeDto)
                                       .price(TEN)
                                       .vin(VIN)
                                       .modelYear(NEW_MODEL_YEAR)
                                       .build();
    }

    @Test
    public void createRetrieveAndUpdateVehicle() {
        final ResponseEntity<VehicleDto> createdEntity = this.restTemplate.postForEntity(HOST + port + VEHICLES_ENDPOINT,
                                                                                         vehicle,
                                                                                         VehicleDto.class);
        assertThat(createdEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final Long vehicleId = Objects.requireNonNull(createdEntity.getBody()).getVehicleId();

        final ResponseEntity<VehicleDto> retrievedEntity = this.restTemplate.getForEntity(HOST + port + VEHICLES_ENDPOINT + SEPARATOR + vehicleId,
                                                                                          VehicleDto.class);
        assertThat(retrievedEntity.getBody()).isEqualTo(createdEntity.getBody());

        this.restTemplate.put(HOST + port + VEHICLES_ENDPOINT + SEPARATOR + vehicleId,
                              vehicleToBeUpdated,
                              VehicleDto.class);

        final ResponseEntity<VehicleDto> updatedEntity = this.restTemplate.getForEntity(HOST + port + VEHICLES_ENDPOINT + SEPARATOR + vehicleId,
                                                                                        VehicleDto.class);
        assertThat(updatedEntity.getBody()).isEqualTo(vehicleToBeUpdated);
    }
}