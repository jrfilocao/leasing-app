package com.fakegmbh.leasingapp.vehicle;

import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import com.fakegmbh.leasingapp.vehicle.model.VehicleMapper;
import com.fakegmbh.leasingapp.vehicle.model.exception.VehicleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleDto getVehicle(final Long vehicleId) {
        final VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                                                             .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        return vehicleMapper.mapTo(vehicleEntity);
    }

    @Transactional
    public VehicleDto updateVehicle(final Long vehicleId, final VehicleDto vehicleToBeUpdated) {
        final VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                                                             .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        vehicleToBeUpdated.setVehicleId(vehicleEntity.getVehicleId());
        final VehicleEntity vehicleEntityToBeUpdated = vehicleMapper.mapTo(vehicleToBeUpdated);
        final VehicleEntity updatedVehicleEntity = vehicleRepository.save(vehicleEntityToBeUpdated);
        return vehicleMapper.mapTo(updatedVehicleEntity);
    }

    @Transactional
    public VehicleDto createVehicle(final VehicleDto vehicleDto) {
        final VehicleEntity vehicleEntity = vehicleMapper.mapTo(vehicleDto);
        final VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntity);
        return vehicleMapper.mapTo(persistedVehicleEntity);
    }
}