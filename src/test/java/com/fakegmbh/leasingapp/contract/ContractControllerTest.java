package com.fakegmbh.leasingapp.contract;

import com.fakegmbh.leasingapp.contract.model.ContractDto;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
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
import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    private static final String CONTRACTS_ENDPOINT = "/api/contracts";
    private static final String PATH_ID = "/{contractId}";
    private static final long CONTRACT_ID = 1L;
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
    private static final String CONTRACT_NUMBER = "1234567";
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("20.00");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    private ContractDto contract;
    private ContractDto contractWithoutId;
    private ContractDto contractWithoutMonthlyRate;
    private ContractDto contractWithoutContractNumber;
    private ContractDto contractWithoutVehicle;
    private ContractDto contractWithoutCustomer;

    @BeforeEach
    public void setUp() {
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

        final LocalDate birthdate = LocalDate.of(BIRTH_YEAR, Month.APRIL, BIRTHDAY);
        final CustomerDto customerDto = CustomerDto.builder()
                                                   .customerId(CUSTOMER_ID)
                                                   .firstName(FIRST_NAME)
                                                   .lastName(LAST_NAME)
                                                   .birthdate(birthdate)
                                                   .build();

        contract = ContractDto.builder()
                              .contractId(CONTRACT_ID)
                              .contractNumber(CONTRACT_NUMBER)
                              .monthlyRate(MONTHLY_RATE)
                              .customer(customerDto)
                              .vehicle(vehicleDto)
                              .build();

        contractWithoutId = ContractDto.builder()
                                       .contractNumber(CONTRACT_NUMBER)
                                       .monthlyRate(MONTHLY_RATE)
                                       .customer(customerDto)
                                       .vehicle(vehicleDto)
                                       .build();

        contractWithoutCustomer = ContractDto.builder()
                                             .contractId(CONTRACT_ID)
                                             .contractNumber(CONTRACT_NUMBER)
                                             .monthlyRate(MONTHLY_RATE)
                                             .vehicle(vehicleDto)
                                             .build();

        contractWithoutVehicle = ContractDto.builder()
                                            .contractId(CONTRACT_ID)
                                            .contractNumber(CONTRACT_NUMBER)
                                            .monthlyRate(MONTHLY_RATE)
                                            .customer(customerDto)
                                            .build();


        contractWithoutContractNumber = ContractDto.builder()
                                                   .contractId(CONTRACT_ID)
                                                   .monthlyRate(MONTHLY_RATE)
                                                   .customer(customerDto)
                                                   .vehicle(vehicleDto)
                                                   .build();

        contractWithoutMonthlyRate = ContractDto.builder()
                                                .contractId(CONTRACT_ID)
                                                .contractNumber(CONTRACT_NUMBER)
                                                .customer(customerDto)
                                                .vehicle(vehicleDto)
                                                .build();
    }

    @Test
    public void whenValidIdThenGetContract() throws Exception {
        when(contractService.getContract(CONTRACT_ID)).thenReturn(contract);

        this.mockMvc.perform(get(CONTRACTS_ENDPOINT + PATH_ID, CONTRACT_ID))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(contract)));
    }

    @Test
    public void whenValidContractThenCreateHim() throws Exception {
        when(contractService.createContract(contract)).thenReturn(contract);

        this.mockMvc.perform(
                post(CONTRACTS_ENDPOINT)
                        .content(asJsonString(contract))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(asJsonString(contract)));
    }

    @Test
    public void whenNoMonthlyRateThenBadRequestOnCreation() throws Exception {
        this.mockMvc.perform(
                post(CONTRACTS_ENDPOINT)
                        .content(asJsonString(contractWithoutMonthlyRate))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoContractNumberThenBadRequestOnCreation() throws Exception {
        this.mockMvc.perform(
                post(CONTRACTS_ENDPOINT)
                        .content(asJsonString(contractWithoutContractNumber))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoCustomerThenBadRequestOnCreation() throws Exception {
        this.mockMvc.perform(
                post(CONTRACTS_ENDPOINT)
                        .content(asJsonString(contractWithoutCustomer))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNoVehicleThenBadRequestOnCreation() throws Exception {
        this.mockMvc.perform(
                post(CONTRACTS_ENDPOINT)
                        .content(asJsonString(contractWithoutVehicle))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateContract() throws Exception {
        when(contractService.updateContract(CONTRACT_ID, contractWithoutId)).thenReturn(contract);

        this.mockMvc.perform(
                put(CONTRACTS_ENDPOINT + PATH_ID, CONTRACT_ID)
                        .content(asJsonString(contractWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(contract)));
    }

    private String asJsonString(final Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }
}