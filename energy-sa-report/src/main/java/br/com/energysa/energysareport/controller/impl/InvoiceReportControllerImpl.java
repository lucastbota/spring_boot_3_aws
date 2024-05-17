package br.com.energysa.energysareport.controller.impl;

import br.com.energysa.energysareport.controller.ReportController;
import br.com.energysa.energysareport.dto.CustomerDataDTO;
import br.com.energysa.energysareport.facade.IReportFacade;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvoiceReportControllerImpl implements ReportController {

    private final IReportFacade reportFacade;

    public InvoiceReportControllerImpl(IReportFacade reportFacade) {
        this.reportFacade = reportFacade;
    }

    @Override
    @RateLimiter(name = "getReport")
    @GetMapping("/invoices/{invoiceId}/customers/{customerId}")
    public ResponseEntity<CustomerDataDTO> getBy(@PathVariable("invoiceId") Long invoiceId, @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(reportFacade.get(invoiceId, customerId));
    }

    @Override
    @GetMapping("/invoices")
    public ResponseEntity<List<CustomerDataDTO>> getAll() {
        return ResponseEntity.ok(reportFacade.getAllCustomers());
    }
}
