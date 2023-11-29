package br.com.energysa.energysainvoice.controller;

import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface InvoiceController {
    ResponseEntity<BigDecimal> getTotal(Long customerId);
}
