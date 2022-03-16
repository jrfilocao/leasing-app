package com.fakegmbh.leasingapp.contract;

import com.fakegmbh.leasingapp.contract.model.ContractDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = {"/api/contracts"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto createContract(@Valid @RequestBody final ContractDto contract) {
        return contractService.createContract(contract);
    }

    @PutMapping(value = "/{contractId}", consumes = APPLICATION_JSON_VALUE)
    public ContractDto updateContract(@PathVariable final Long contractId,
                                      @Valid @RequestBody final ContractDto contract) {
        return contractService.updateContract(contractId, contract);
    }

    @GetMapping("/{contractId}")
    public ContractDto getContract(@PathVariable final Long contractId) {
        return contractService.getContract(contractId);
    }
}