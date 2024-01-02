package br.com.energysa.energysareport.service;

import java.math.BigDecimal;

public interface IInvoiceService {
    BigDecimal get(Long invoiceId);
}
