package com.fakegmbh.leasingapp.contract.model;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContractMapper {

    private final ModelMapper modelMapper;

    public ContractEntity mapTo(final ContractDto contractDto) {
        return modelMapper.map(contractDto, ContractEntity.class);
    }

    public ContractDto mapTo(final ContractEntity contractEntity) {
        return modelMapper.map(contractEntity, ContractDto.class);
    }
}