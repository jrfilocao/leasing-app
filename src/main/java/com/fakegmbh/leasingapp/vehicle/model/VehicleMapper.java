package com.fakegmbh.leasingapp.vehicle.model;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VehicleMapper {

    private final ModelMapper modelMapper;

    public VehicleEntity mapTo(final VehicleDto vehicleDto) {
        return modelMapper.map(vehicleDto, VehicleEntity.class);
    }

    public VehicleDto mapTo(final VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDto.class);
    }
}