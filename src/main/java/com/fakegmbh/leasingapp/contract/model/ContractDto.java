package com.fakegmbh.leasingapp.contract.model;

import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDto {

    private Long contractId;

    @NotEmpty
    private String contractNumber;

    @NotNull
    @Min(0)
    private BigDecimal monthlyRate;

    @Valid
    @NotNull
    private VehicleDto vehicle;

    @Valid
    @NotNull
    private CustomerDto customer;
}