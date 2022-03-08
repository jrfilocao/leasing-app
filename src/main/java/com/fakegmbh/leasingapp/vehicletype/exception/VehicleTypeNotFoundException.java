package com.fakegmbh.leasingapp.vehicletype.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class VehicleTypeNotFoundException extends RuntimeException {

    private final Long vehicleTypeId;

    public VehicleTypeNotFoundException(final Long vehicleTypeId) {
        super();
        this.vehicleTypeId = vehicleTypeId;
    }
}