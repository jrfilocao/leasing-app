package com.fakegmbh.leasingapp.contract;

import com.fakegmbh.leasingapp.contract.exception.ContractNotFoundException;
import com.fakegmbh.leasingapp.contract.model.ContractDto;
import com.fakegmbh.leasingapp.contract.model.ContractEntity;
import com.fakegmbh.leasingapp.contract.model.ContractMapper;
import com.fakegmbh.leasingapp.customer.CustomerRepository;
import com.fakegmbh.leasingapp.customer.exception.CustomerNotFoundException;
import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.vehicle.VehicleRepository;
import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import com.fakegmbh.leasingapp.vehicle.model.exception.VehicleNotFoundException;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class ContractServiceTest {

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

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ContractMapper contractMapper;

    @InjectMocks
    private ContractService contractService;

    private ContractDto contractDtoWithoutId;
    private ContractDto contractDto;
    private ContractEntity contract;
    private CustomerEntity customer;
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

        customer = CustomerEntity.builder()
                                 .customerId(CUSTOMER_ID)
                                 .firstName(FIRST_NAME)
                                 .lastName(LAST_NAME)
                                 .birthdate(birthdate)
                                 .updatedAt(Timestamp.from(Instant.now()))
                                 .createdAt(Timestamp.from(Instant.now()))
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
        contractDtoWithoutId = ContractDto.builder()
                                          .contractId(CONTRACT_ID)
                                          .contractNumber(CONTRACT_NUMBER)
                                          .customer(customerDto)
                                          .vehicle(vehicleDto)
                                          .build();

    }

    @Test
    public void testGetValidContract() {
        when(contractRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(contract));
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);

        assertThat(contractService.getContract(VEHICLE_ID)).isEqualTo(contractDto);
    }

    @Test
    public void testGetInvalidContract() {
        when(contractRepository.findById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractService.getContract(VEHICLE_ID)).isInstanceOf(ContractNotFoundException.class);
    }

    @Test
    public void whenValidContractThenCreateIt() {
        when(contractMapper.mapTo(contractDtoWithoutId)).thenReturn(contract);
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);
        when(contractRepository.save(contract)).thenReturn(contract);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.ofNullable(customer));
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.ofNullable(vehicle));
        when(contractRepository.save(contract)).thenReturn(contract);
        assertThat(contractService.createContract(contractDtoWithoutId)).isEqualTo(contractDto);
    }

    @Test
    public void whenNoCustomerThenNotFoundExceptionOnCreation() {
        when(contractMapper.mapTo(contractDtoWithoutId)).thenReturn(contract);
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);
        when(contractRepository.save(contract)).thenReturn(contract);
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.ofNullable(vehicle));
        when(contractRepository.save(contract)).thenReturn(contract);

        assertThatThrownBy(() -> contractService.createContract(contractDtoWithoutId)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    public void whenNoVehicleThenNotFoundExceptionOnCreation() {
        when(contractMapper.mapTo(contractDtoWithoutId)).thenReturn(contract);
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);
        when(contractRepository.save(contract)).thenReturn(contract);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.ofNullable(customer));
        when(contractRepository.save(contract)).thenReturn(contract);

        assertThatThrownBy(() -> contractService.createContract(contractDtoWithoutId)).isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void whenValidIdThenUpdateContract() {
        when(contractRepository.findById(CONTRACT_ID)).thenReturn(Optional.of(contract));
        when(contractRepository.save(contract)).thenReturn(contract);
        when(contractMapper.mapTo(contractDto)).thenReturn(contract);
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.ofNullable(customer));
        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.ofNullable(vehicle));

        assertThat(contractService.updateContract(CONTRACT_ID, contractDtoWithoutId)).isEqualTo(contractDto);
    }

    @Test
    public void whenNoVehicleThenNotFoundExceptionOnUpdate() {
        when(contractRepository.findById(CONTRACT_ID)).thenReturn(Optional.of(contract));
        when(contractRepository.save(contract)).thenReturn(contract);
        when(contractMapper.mapTo(contractDto)).thenReturn(contract);
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.ofNullable(customer));
        assertThatThrownBy(() -> contractService.updateContract(CONTRACT_ID, contractDtoWithoutId)).isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void whenNoCustomerThenNotFoundExceptionOnUpdate() {
        when(contractRepository.findById(CONTRACT_ID)).thenReturn(Optional.of(contract));
        when(contractRepository.save(contract)).thenReturn(contract);
        when(contractMapper.mapTo(contractDto)).thenReturn(contract);
        when(contractMapper.mapTo(contract)).thenReturn(contractDto);

        when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.ofNullable(vehicle));

        assertThatThrownBy(() -> contractService.updateContract(CONTRACT_ID, contractDtoWithoutId)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    public void whenInvalidContractIdThenNotFoundOnUpdate() {
        when(contractRepository.findById(CONTRACT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractService.updateContract(CONTRACT_ID, contractDtoWithoutId)).isInstanceOf(
                ContractNotFoundException.class);
    }
}