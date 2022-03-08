package com.fakegmbh.leasingapp.vehicle;

import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    private static final String VEHICLES_ENDPOINT = "/api/vehicles";
    private static final String PATH_ID = "/{vehicleId}";
    private static final long VEHICLE_ID = 1L;
    private static final long VEHICLE_TYPE_ID = 10L;
    private static final String BRAND = "Bmw";
    private static final String MODEL = "x3";
    private static final String VIN = "12345";
    private static final int MODEL_YEAR = 2015;
    private static final int INVALID_MODEL_YEAR = 1000;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private VehicleDto vehicle;
    private VehicleDto vehicleWithoutId;
    private VehicleDto vehicleWithoutVin;
    private VehicleDto vehicleWithoutVehicleType;
    private VehicleDto vehicleWithoutModelYear;
    private VehicleDto vehicleWithInvalidModelYear;
    private VehicleDto vehicleWithoutPrice;

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
                            .price(BigDecimal.TEN)
                            .vin(VIN)
                            .modelYear(MODEL_YEAR)
                            .build();

        vehicleWithoutVin = VehicleDto.builder()
                                      .vehicleType(vehicleTypeDto)
                                      .price(BigDecimal.TEN)
                                      .modelYear(MODEL_YEAR)
                                      .build();

        vehicleWithoutVehicleType = VehicleDto.builder()
                                              .price(BigDecimal.TEN)
                                              .vin(VIN)
                                              .modelYear(MODEL_YEAR)
                                              .build();

        vehicleWithoutModelYear = VehicleDto.builder()
                            .vehicleId(VEHICLE_ID)
                            .vehicleType(vehicleTypeDto)
                            .price(BigDecimal.TEN)
                            .vin(VIN)
                            .build();

        vehicleWithInvalidModelYear = VehicleDto.builder()
                                            .vehicleId(VEHICLE_ID)
                                            .vehicleType(vehicleTypeDto)
                                            .price(BigDecimal.TEN)
                                            .modelYear(INVALID_MODEL_YEAR)
                                            .vin(VIN)
                                            .build();

        vehicleWithoutPrice = VehicleDto.builder()
                                        .vehicleId(VEHICLE_ID)
                                        .vehicleType(vehicleTypeDto)
                                        .modelYear(MODEL_YEAR)
                                        .vin(VIN)
                                        .build();

        vehicleWithoutId = VehicleDto.builder()
                                     .vehicleType(vehicleTypeDto)
                                     .price(BigDecimal.TEN)
                                     .vin(VIN)
                                     .modelYear(MODEL_YEAR)
                                     .build();
    }

    @Test
    public void whenValidIdThenGetVehicle() throws Exception {
        when(vehicleService.getVehicle(VEHICLE_ID)).thenReturn(vehicle);

        this.mockMvc.perform(get(VEHICLES_ENDPOINT + PATH_ID, VEHICLE_ID))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(vehicle)));
    }

    @Test
    public void whenValidVehicleThenCreateHim() throws Exception {
        when(vehicleService.createVehicle(vehicle)).thenReturn(vehicle);

        this.mockMvc.perform(
                post(VEHICLES_ENDPOINT)
                        .content(asJsonString(vehicle))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(asJsonString(vehicle)));
    }

    @Test
    public void whenVehicleWithoutVinThenCreateHim() throws Exception {
        when(vehicleService.createVehicle(vehicleWithoutVin)).thenReturn(vehicleWithoutVin);

        this.mockMvc.perform(
                post(VEHICLES_ENDPOINT)
                        .content(asJsonString(vehicleWithoutVin))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(asJsonString(vehicleWithoutVin)));
    }

    @Test
    public void whenNoVehicleTypeThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(VEHICLES_ENDPOINT)
                        .content(asJsonString(vehicleWithoutVehicleType))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoModelYearThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(VEHICLES_ENDPOINT)
                        .content(asJsonString(vehicleWithoutModelYear))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenInvalidModelYearThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(VEHICLES_ENDPOINT)
                        .content(asJsonString(vehicleWithInvalidModelYear))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoPriceThenBadRequest() throws Exception {
        this.mockMvc.perform(
                post(VEHICLES_ENDPOINT)
                        .content(asJsonString(vehicleWithoutPrice))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateVehicle() throws Exception {
        when(vehicleService.updateVehicle(VEHICLE_ID, vehicleWithoutId)).thenReturn(vehicle);

        this.mockMvc.perform(
                put(VEHICLES_ENDPOINT + PATH_ID, VEHICLE_ID)
                        .content(asJsonString(vehicleWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(vehicle)));
    }

    private String asJsonString(final Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}