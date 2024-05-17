package br.com.energysa.energysareport.controller;

import br.com.energysa.energysareport.dto.CustomerDataDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ReportController {
    ResponseEntity<CustomerDataDTO> getBy(Long invoiceId, Long customerId);
    ResponseEntity<List<CustomerDataDTO>> getAll();
}