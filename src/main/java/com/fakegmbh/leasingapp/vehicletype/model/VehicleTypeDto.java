package com.fakegmbh.leasingapp.vehicletype.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleTypeDto {

    private Long vehicleTypeId;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;
}