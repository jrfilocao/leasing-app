package com.fakegmbh.leasingapp.contract;

import com.fakegmbh.leasingapp.contract.exception.ContractNotFoundException;
import com.fakegmbh.leasingapp.contract.model.ContractDto;
import com.fakegmbh.leasingapp.contract.model.ContractEntity;
import com.fakegmbh.leasingapp.contract.model.ContractMapper;
import com.fakegmbh.leasingapp.customer.CustomerRepository;
import com.fakegmbh.leasingapp.customer.exception.CustomerNotFoundException;
import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.vehicle.VehicleRepository;
import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import com.fakegmbh.leasingapp.vehicle.model.exception.VehicleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class ContractService {

    private static final boolean NOT_PARALLEL = false;
    private final ContractRepository contractRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ContractMapper contractMapper;

    public ContractDto getContract(final Long contractId) {
        final ContractEntity contractEntity = contractRepository.findById(contractId)
                                                                .orElseThrow(() -> new ContractNotFoundException(contractId));
        return contractMapper.mapTo(contractEntity);
    }

    @Transactional
    public ContractDto updateContract(final Long contractId, final ContractDto contractToBeUpdated) {
        final ContractEntity contractEntity = contractRepository.findById(contractId)
                                                                .orElseThrow(() -> new ContractNotFoundException(contractId));
        contractToBeUpdated.setContractId(contractEntity.getContractId());
        final ContractEntity contractEntityToBeUpdated = contractMapper.mapTo(contractToBeUpdated);
        final CustomerEntity persistedCustomerEntity = requireExistentCustomer(contractEntityToBeUpdated.getCustomer());
        final VehicleEntity persistedVehicleEntity = requireExistentVehicle(contractEntityToBeUpdated.getVehicle());
        contractEntityToBeUpdated.setVehicle(persistedVehicleEntity);
        contractEntityToBeUpdated.setCustomer(persistedCustomerEntity);
        final ContractEntity updatedContractEntity = contractRepository.save(contractEntityToBeUpdated);
        return contractMapper.mapTo(updatedContractEntity);
    }

    @Transactional
    public ContractDto createContract(final ContractDto contractDto) {
        final ContractEntity contractEntity = contractMapper.mapTo(contractDto);
        final CustomerEntity persistedCustomerEntity = requireExistentCustomer(contractEntity.getCustomer());
        final VehicleEntity persistedVehicleEntity = requireExistentVehicle(contractEntity.getVehicle());
        contractEntity.setCustomer(requireExistentCustomer(persistedCustomerEntity));
        contractEntity.setVehicle(requireExistentVehicle(persistedVehicleEntity));
        final ContractEntity persistedContractEntity = contractRepository.save(contractEntity);
        return contractMapper.mapTo(persistedContractEntity);
    }

    private VehicleEntity requireExistentVehicle(final VehicleEntity vehicleEntity) {
        Optional.ofNullable(vehicleEntity.getVehicleId()).orElseThrow(() -> new VehicleNotFoundException(null));
        return vehicleRepository.findById(vehicleEntity.getVehicleId())
                                .orElseThrow(() -> new VehicleNotFoundException(vehicleEntity.getVehicleId()));
    }

    private CustomerEntity requireExistentCustomer(final CustomerEntity customerEntity) {
        Optional.ofNullable(customerEntity.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException(null));
        return customerRepository.findById(customerEntity.getCustomerId())
                                 .orElseThrow(() -> new CustomerNotFoundException(customerEntity.getCustomerId()));
    }

    public List<ContractDto> getContracts() {
        return StreamSupport.stream(contractRepository.findAll().spliterator(), NOT_PARALLEL)
                            .map(contractMapper::mapTo)
                            .collect(Collectors.toList());
    }
}