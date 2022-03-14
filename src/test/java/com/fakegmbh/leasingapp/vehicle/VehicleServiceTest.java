package com.fakegmbh.leasingapp.vehicle;

import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import com.fakegmbh.leasingapp.vehicle.model.VehicleMapper;
import com.fakegmbh.leasingapp.vehicle.model.exception.VehicleNotFoundException;
import com.fakegmbh.leasingapp.vehicletype.VehicleTypeRepository;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class VehicleServiceTest {

    private static final long VEHICLE_ID = 1L;
    private static final long VEHICLE_TYPE_ID = 10L;
    private static final String BRAND = "Bmw";
    private static final String MODEL = "x3";
    private static final String VIN = "12345";
    private static final int MODEL_YEAR = 2015;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    private VehicleDto vehicleDtoWithoutId;
    private VehicleDto vehicleDto;
    private VehicleEntity vehicle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        final VehicleTypeEntity vehicleType = VehicleTypeEntity.builder()
                                                               .vehicleTypeId(VEHICLE_TYPE_ID)
                                                               .brand(BRAND)
                                                               .model(MODEL)
                                                               .createdAt(Timestamp.from(Instant.now()))
                                                               .build();
        when(vehicleTypeRepository.findByBrandAndModel(eq(BRAND), eq(MODEL))).thenReturn(Optional.of(vehicleType));
        vehicle = VehicleEntity.builder()
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
        vehicleDto = VehicleDto.builder()
                               .vehicleId(VEHICLE_ID)
                               .vehicleType(vehicleTypeDto)
                               .price(BigDecimal.TEN)
                               .vin(VIN)
                               .modelYear(MODEL_YEAR)
                               .build();

        vehicleDtoWithoutId = VehicleDto.builder()
                               .vehicleType(vehicleTypeDto)
                               .price(BigDecimal.TEN)
                               .vin(VIN)
                               .modelYear(MODEL_YEAR)
                               .build();
    }

    @Test
    public void testGetValidVehicle() {
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.mapTo(vehicle)).thenReturn(vehicleDto);

        assertThat(vehicleService.getVehicle(VEHICLE_ID)).isEqualTo(vehicleDto);
    }

    @Test
    public void testGetInvalidVehicle() {
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.getVehicle(VEHICLE_ID)).isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void testCreateVehicle() {
        when(vehicleMapper.mapTo(vehicleDtoWithoutId)).thenReturn(vehicle);
        when(vehicleMapper.mapTo(vehicle)).thenReturn(vehicleDto);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        assertThat(vehicleService.createVehicle(vehicleDtoWithoutId)).isEqualTo(vehicleDto);
    }

    @Test
    public void testUpdateVehicleValidId() {
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.mapTo(vehicleDto)).thenReturn(vehicle);
        when(vehicleMapper.mapTo(vehicle)).thenReturn(vehicleDto);

        assertThat(vehicleService.updateVehicle(VEHICLE_ID, vehicleDtoWithoutId)).isEqualTo(vehicleDto);
    }

    @Test
    public void testUpdateVehicleInvalidId() {
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.updateVehicle(VEHICLE_ID, vehicleDtoWithoutId)).isInstanceOf(
                VehicleNotFoundException.class);
    }
}