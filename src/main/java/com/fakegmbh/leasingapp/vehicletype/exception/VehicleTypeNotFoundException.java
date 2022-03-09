package com.fakegmbh.leasingapp.vehicletype.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class VehicleTypeNotFoundException extends RuntimeException {

    private final String brand;
    private final String model;

    public VehicleTypeNotFoundException(final String brand, final String model) {
        super();
        this.brand = brand;
        this.model = model;
    }
}