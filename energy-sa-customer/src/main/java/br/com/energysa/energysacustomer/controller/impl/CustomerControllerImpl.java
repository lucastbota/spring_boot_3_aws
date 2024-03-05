package br.com.energysa.energysacustomer.controller.impl;

import br.com.energysa.energysacustomer.controller.CustomerController;
import br.com.energysa.energysacustomer.dto.CustomerDTO;
import br.com.energysa.energysacustomer.service.CustomerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService service;

    public CustomerControllerImpl(CustomerService service) {
        this.service = service;
    }

    @Override
    @RateLimiter(name="createCustomer")
    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        var data = service.create(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerDTO(data.id(), data.name(), data.addressData().latitude(), data.addressData().longitude()));
    }

    @Override
    @RateLimiter(name="getCustomer")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable("id") Long id) {
        var data = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerDTO(data.id(), data.name(), data.addressData().latitude(), data.addressData().longitude()));
    }
}
