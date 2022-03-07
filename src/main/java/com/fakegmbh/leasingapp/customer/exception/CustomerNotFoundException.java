package com.fakegmbh.leasingapp.customer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CustomerNotFoundException extends RuntimeException {

    private final Long customerId;

    public CustomerNotFoundException(final Long customerId) {
        super();
        this.customerId = customerId;
    }
}
