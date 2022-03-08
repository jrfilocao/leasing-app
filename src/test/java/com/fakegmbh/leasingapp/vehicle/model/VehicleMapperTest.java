package com.fakegmbh.leasingapp.vehicle.model;

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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class VehicleMapperTest {

    private static final long VEHICLE_ID = 1L;
    private static final long VEHICLE_TYPE_ID = 10L;
    private static final String BRAND = "Bmw";
    private static final String MODEL = "x3";
    private static final String VIN = "12345";
    private static final int MODEL_YEAR = 2015;

    private ModelMapper modelMapper;

    private VehicleMapper vehicleMapper;

    private VehicleEntity vehicle;
    private VehicleDto vehicleDto;
    private VehicleEntity vehicleWithoutTimestamps;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        vehicleMapper = new VehicleMapper(modelMapper);
        final VehicleTypeEntity vehicleType = VehicleTypeEntity.builder()
                                                               .vehicleTypeId(VEHICLE_TYPE_ID)
                                                               .brand(BRAND)
                                                               .model(MODEL)
                                                               .createdAt(Timestamp.from(Instant.now()))
                                                               .build();
        final VehicleTypeEntity vehicleTypeWithoutTimestamps = VehicleTypeEntity.builder()
                                                                                .vehicleTypeId(VEHICLE_TYPE_ID)
                                                                                .brand(BRAND)
                                                                                .model(MODEL)
                                                                                .build();
        vehicle = VehicleEntity.builder()
                               .vehicleId(VEHICLE_ID)
                               .vehicleType(vehicleType)
                               .price(BigDecimal.TEN)
                               .vin(VIN)
                               .modelYear(MODEL_YEAR)
                               .updatedAt(Timestamp.from(Instant.now()))
                               .createdAt(Timestamp.from(Instant.now()))
                               .build();

        vehicleWithoutTimestamps = VehicleEntity.builder()
                                                .vehicleId(VEHICLE_ID)
                                                .vehicleType(vehicleTypeWithoutTimestamps)
                                                .price(BigDecimal.TEN)
                                                .vin(VIN)
                                                .modelYear(MODEL_YEAR)
                                                .build();
        final VehicleTypeDto vehicleTypeDto = VehicleTypeDto.builder()
                                                            .vehicleTypeId(VEHICLE_TYPE_ID)
                                                            .brand(BRAND)
                                                            .model(MODEL)
                                                            .build();
        vehicleDto = VehicleDto.builder()
                               .vehicleId(VEHICLE_ID)
                               .vehicleType(vehicleTypeDto)
                               .price(BigDecimal.TEN)
                               .vin(VIN)
                               .modelYear(MODEL_YEAR)
                               .build();
    }

    @Test
    public void testMapToVehicleTypeDto() {
        assertThat(vehicleMapper.mapTo(vehicle)).isEqualTo(vehicleDto);
    }

    @Test
    public void testMapToVehicleType() {
        assertThat(vehicleMapper.mapTo(vehicleDto)).isEqualTo(vehicleWithoutTimestamps);
    }
}