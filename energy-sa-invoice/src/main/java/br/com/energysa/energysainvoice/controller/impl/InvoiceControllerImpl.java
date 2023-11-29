package br.com.energysa.energysainvoice.controller.impl;

import br.com.energysa.energysainvoice.controller.InvoiceController;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/invoices")
public class InvoiceControllerImpl implements InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceControllerImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    @GetMapping("/{customerId}")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(invoiceService.getTotal(customerId));
    }
}
