package br.com.energysa.energysainvoice.controller;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface InvoiceController {
    ResponseEntity<BigDecimal> getTotal(Long id);
}
