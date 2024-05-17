package br.com.energysa.energysainvoice.controller;

import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.dto.InvoiceIdDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface InvoiceController {
    ResponseEntity<InvoiceDTO> getInvoice(Long id);
    CollectionModel<InvoiceIdDTO> getInvoiceKeys();
}
