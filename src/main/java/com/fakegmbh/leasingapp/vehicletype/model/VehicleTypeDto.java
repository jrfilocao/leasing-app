package com.fakegmbh.leasingapp.vehicletype.model;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "BMW")
    @NotEmpty
    private String brand;

    @ApiModelProperty(example = "X1")
    @NotEmpty
    private String model;
}