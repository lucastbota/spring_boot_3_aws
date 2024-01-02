package br.com.energysa.energysareport.service;

import br.com.energysa.energysareport.dto.CustomerDTO;

public interface ICustomerService {
    CustomerDTO get(Long customerId);
}
