package br.com.energysa.energysareport.dto;

import java.math.BigDecimal;

public record InvoiceDTO(Long customerId, Double kwh, BigDecimal total){
}
