package br.com.energysa.energysainvoice.service;

import br.com.energysa.energysainvoice.aws.s3.contract.InvoiceData;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    void create(InvoiceDTO dto);
    InvoiceData getInvoice(Long id);
    List<String> getInvoiceKeys();
}
