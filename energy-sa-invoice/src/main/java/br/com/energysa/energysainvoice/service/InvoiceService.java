package br.com.energysa.energysainvoice.service;

import br.com.energysa.energysainvoice.aws.s3.contract.InvoiceData;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;

import java.math.BigDecimal;

public interface InvoiceService {
    void create(InvoiceDTO dto);
    BigDecimal getTotal(Long id);
}
