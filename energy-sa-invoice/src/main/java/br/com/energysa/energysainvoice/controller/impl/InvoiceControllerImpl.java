package br.com.energysa.energysainvoice.controller.impl;

import br.com.energysa.energysainvoice.controller.InvoiceController;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.logging.Logger;

@RestController
@RequestMapping("/invoices")
public class InvoiceControllerImpl implements InvoiceController {
    private static final Logger LOGGER = Logger.getLogger(InvoiceControllerImpl.class.getName());
    private final InvoiceService invoiceService;

    public InvoiceControllerImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable("id") Long id) {
        LOGGER.info(String.format("Invoice id: %s", id));
        return ResponseEntity.ok(invoiceService.getTotal(id));
    }
}
