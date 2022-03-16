package com.fakegmbh.leasingapp.contract.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ContractNotFoundException extends RuntimeException {

    private final Long contractId;

    public ContractNotFoundException(final Long contractId) {
        super();
        this.contractId = contractId;
    }
}
