package com.fakegmbh.leasingapp.vehicle.model;

import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDto {

    private Long vehicleId;

    @Valid
    @JsonUnwrapped
    private VehicleTypeDto vehicleType;

    @Min(1500)
    private int modelYear;

    private String vin;

    @NotNull
    private BigDecimal price;
}
