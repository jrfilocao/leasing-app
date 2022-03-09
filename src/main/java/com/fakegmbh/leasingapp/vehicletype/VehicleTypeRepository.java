package com.fakegmbh.leasingapp.vehicletype;

import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends CrudRepository<VehicleTypeEntity, Long> {

    Optional<VehicleTypeEntity> findByBrandAndModel(String brand, String model);
}