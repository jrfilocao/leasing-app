package com.fakegmbh.leasingapp.contract.model;

import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ContractMapperTest {

    private static final long VEHICLE_ID = 1L;
    private static final long VEHICLE_TYPE_ID = 10L;
    private static final String BRAND = "Bmw";
    private static final String MODEL = "x3";
    private static final String VIN = "12345";
    private static final int MODEL_YEAR = 2015;
    private static final long CUSTOMER_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int BIRTH_YEAR = 1985;
    private static final int BIRTHDAY = 30;
    private static final Long CONTRACT_ID = 1L;
    private static final String CONTRACT_NUMBER = "1234567";


    private ContractMapper contractMapper;

    private ContractDto contractDto;
    private ContractEntity contract;
    private ContractEntity contractWithoutTimestamps;

    @BeforeEach
    public void setUp() {
        final ModelMapper modelMapper = new ModelMapper();
        contractMapper = new ContractMapper(modelMapper);
        final VehicleTypeEntity vehicleType = VehicleTypeEntity.builder()
                                                               .vehicleTypeId(VEHICLE_TYPE_ID)
                                                               .brand(BRAND)
                                                               .model(MODEL)
                                                               .createdAt(Timestamp.from(Instant.now()))
                                                               .build();
        final VehicleEntity vehicle = VehicleEntity.builder()
                                                   .vehicleId(VEHICLE_ID)
                                                   .vehicleType(vehicleType)
                                                   .price(BigDecimal.TEN)
                                                   .vin(VIN)
                                                   .modelYear(MODEL_YEAR)
                                                   .updatedAt(Timestamp.from(Instant.now()))
                                                   .createdAt(Timestamp.from(Instant.now()))
                                                   .build();

        final VehicleTypeDto vehicleTypeDto = VehicleTypeDto.builder()
                                                            .vehicleTypeId(VEHICLE_TYPE_ID)
                                                            .brand(BRAND)
                                                            .model(MODEL)
                                                            .build();
        final VehicleDto vehicleDto = VehicleDto.builder()
                                                .vehicleId(VEHICLE_ID)
                                                .vehicleType(vehicleTypeDto)
                                                .price(BigDecimal.TEN)
                                                .vin(VIN)
                                                .modelYear(MODEL_YEAR)
                                                .build();
        final VehicleTypeEntity vehicleTypeWithoutTimestamps = VehicleTypeEntity.builder()
                                                                                .vehicleTypeId(VEHICLE_TYPE_ID)
                                                                                .brand(BRAND)
                                                                                .model(MODEL)
                                                                                .build();
        final VehicleEntity vehicleWithoutTimestamps = VehicleEntity.builder()
                                                                    .vehicleId(VEHICLE_ID)
                                                                    .vehicleType(vehicleTypeWithoutTimestamps)
                                                                    .price(BigDecimal.TEN)
                                                                    .vin(VIN)
                                                                    .modelYear(MODEL_YEAR)
                                                                    .build();

        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        final CustomerDto customerDto = CustomerDto.builder()
                                                   .customerId(CUSTOMER_ID)
                                                   .firstName(FIRST_NAME)
                                                   .lastName(LAST_NAME)
                                                   .birthdate(birthdate)
                                                   .build();

        final CustomerEntity customer = CustomerEntity.builder()
                                                      .customerId(CUSTOMER_ID)
                                                      .firstName(FIRST_NAME)
                                                      .lastName(LAST_NAME)
                                                      .birthdate(birthdate)
                                                      .updatedAt(Timestamp.from(Instant.now()))
                                                      .createdAt(Timestamp.from(Instant.now()))
                                                      .build();

        final CustomerEntity customerWithoutTimestamps = CustomerEntity.builder()
                                                                       .customerId(CUSTOMER_ID)
                                                                       .firstName(FIRST_NAME)
                                                                       .lastName(LAST_NAME)
                                                                       .birthdate(birthdate)
                                                                       .build();
        contractDto = ContractDto.builder()
                                 .contractId(CONTRACT_ID)
                                 .contractNumber(CONTRACT_NUMBER)
                                 .customer(customerDto)
                                 .vehicle(vehicleDto)
                                 .build();
        contract = ContractEntity.builder()
                .contractId(CONTRACT_ID)
                .contractNumber(CONTRACT_NUMBER)
                .vehicle(vehicle)
                .customer(customer)
                .updatedAt(Timestamp.from(Instant.now()))
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        contractWithoutTimestamps = ContractEntity.builder()
                                 .contractId(CONTRACT_ID)
                                 .contractNumber(CONTRACT_NUMBER)
                                 .vehicle(vehicleWithoutTimestamps)
                                 .customer(customerWithoutTimestamps)
                                 .build();
    }

    @Test
    public void testMapToVehicleTypeDto() {
        assertThat(contractMapper.mapTo(contract)).isEqualTo(contractDto);
    }

    @Test
    public void testMapToVehicleType() {
        assertThat(contractMapper.mapTo(contractDto)).isEqualTo(contractWithoutTimestamps);
    }
}