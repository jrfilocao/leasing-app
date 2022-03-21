package com.fakegmbh.leasingapp.vehicletype.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(name = "VehicleType")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleTypeDto {

    private Long vehicleTypeId;

    @Schema(example = "BMW")
    @NotEmpty
    private String brand;

    @Schema(example = "X1")
    @NotEmpty
    private String model;
}