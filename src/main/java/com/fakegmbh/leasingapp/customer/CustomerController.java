package com.fakegmbh.leasingapp.customer;

import com.fakegmbh.leasingapp.customer.model.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = {"/api/customers"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody final CustomerDto customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping(value = "/{customerId}", consumes = APPLICATION_JSON_VALUE)
    public CustomerDto updateCustomer(@PathVariable final Long customerId,
                                      @Valid @RequestBody final CustomerDto customer) {
        return customerService.updateCustomer(customerId, customer);
    }

    @GetMapping("/{customerId}")
    public CustomerDto getCustomer(@PathVariable final Long customerId) {
        return customerService.getCustomer(customerId);
    }
}