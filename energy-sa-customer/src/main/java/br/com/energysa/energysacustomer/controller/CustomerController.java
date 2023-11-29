package br.com.energysa.energysacustomer.controller;

import br.com.energysa.energysacustomer.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerController {
    ResponseEntity<CustomerDTO> create(CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> findById(Long id);
}
