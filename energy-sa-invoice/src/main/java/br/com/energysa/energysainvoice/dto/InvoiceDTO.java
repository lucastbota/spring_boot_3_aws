package br.com.energysa.energysainvoice.dto;

import java.math.BigDecimal;

public record InvoiceDTO(Long customerId, Double kwh, BigDecimal total){
}
