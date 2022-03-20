package com.fakegmbh.leasingapp.vehicle.model;

import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "2020")
    @Min(1500)
    private int modelYear;

    @ApiModelProperty(example = "BMW45678901234567")
    private String vin;

    @ApiModelProperty(example = "10000.00")
    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT)
    private BigDecimal price;
}
