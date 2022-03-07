package com.fakegmbh.leasingapp.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final HttpStatus status;

    private final int statusValue;

    private final String message;

    private final Collection<?> errors;

    @Builder
    @SuppressWarnings("unused")
    public ApiError(final HttpStatus status, final String message, final Collection<?> errors) {
        this.status = status;
        this.statusValue = status.value();
        this.message = message;
        this.errors = errors;
    }
}
