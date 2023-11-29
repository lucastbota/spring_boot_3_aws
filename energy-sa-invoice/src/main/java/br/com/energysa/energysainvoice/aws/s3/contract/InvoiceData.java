package br.com.energysa.energysainvoice.aws.s3.contract;

import java.math.BigDecimal;

public record InvoiceData(Long customerId, Double kwh, BigDecimal total) {
}