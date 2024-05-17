package br.com.energysa.energysareport.service;

import br.com.energysa.energysareport.dto.CustomerDTO;

import java.util.concurrent.CompletableFuture;

public interface ICustomerService {
    CompletableFuture<CustomerDTO> getCustomerById(Long customerId);
}
