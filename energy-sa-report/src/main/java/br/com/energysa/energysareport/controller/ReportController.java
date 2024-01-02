package br.com.energysa.energysareport.controller;

import br.com.energysa.energysareport.dto.CustomerDataDTO;
import org.springframework.http.ResponseEntity;


public interface ReportController {
    ResponseEntity<CustomerDataDTO> get(Long invoiceId, Long customerId);
}