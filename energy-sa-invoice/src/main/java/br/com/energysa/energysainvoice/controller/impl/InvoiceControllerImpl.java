package br.com.energysa.energysainvoice.controller.impl;

import br.com.energysa.energysainvoice.controller.InvoiceController;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.dto.InvoiceIdDTO;
import br.com.energysa.energysainvoice.service.InvoiceService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable("id") Long id) {
        var invoice = invoiceService.getInvoice(id);
        return ResponseEntity.ok(new InvoiceDTO(invoice.customerId(), invoice.kwh(), invoice.total()));
    }

    @Override
    @GetMapping
    public CollectionModel<InvoiceIdDTO> getInvoiceKeys(){
        var keys = invoiceService.getInvoiceKeys();

        List<InvoiceIdDTO> invoices =  keys.stream().filter(Objects::nonNull).map(k -> {
            InvoiceIdDTO dto = new InvoiceIdDTO(k);
            dto.add(linkTo(InvoiceControllerImpl.class).slash(k).withSelfRel());
            return dto;
        }).toList();

        return CollectionModel.of(invoices, linkTo(InvoiceControllerImpl.class).withSelfRel());
    }
}
