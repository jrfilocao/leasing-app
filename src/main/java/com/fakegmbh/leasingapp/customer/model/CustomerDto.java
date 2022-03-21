package com.fakegmbh.leasingapp.customer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(name = "Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private Long customerId;

    @Schema(example = "John")
    @NotEmpty
    private String firstName;

    @Schema(example = "Doe")
    @NotEmpty
    private String lastName;

    @Schema(example = "1984-10-16")
    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate birthdate;
}
