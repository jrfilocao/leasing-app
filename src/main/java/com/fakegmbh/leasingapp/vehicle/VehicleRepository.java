package com.fakegmbh.leasingapp.vehicle;

import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {
}