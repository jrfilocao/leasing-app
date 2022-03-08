package com.fakegmbh.leasingapp.vehicle.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class VehicleNotFoundException extends RuntimeException {

    private final Long vehicleId;

    public VehicleNotFoundException(final Long vehicleId) {
        super();
        this.vehicleId = vehicleId;
    }
}
