package br.com.energysa.energysacustomer.service;

import br.com.energysa.energysacustomer.aws.s3.contract.CustomerData;
import br.com.energysa.energysacustomer.dto.CustomerDTO;

public interface CustomerService {
    CustomerData create(CustomerDTO customerDTO);
    CustomerData findById(Long id);
}
