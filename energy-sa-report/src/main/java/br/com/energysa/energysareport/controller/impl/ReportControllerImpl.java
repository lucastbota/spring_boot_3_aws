package br.com.energysa.energysareport.controller.impl;

import br.com.energysa.energysareport.controller.ReportController;
import br.com.energysa.energysareport.dto.CustomerDataDTO;
import br.com.energysa.energysareport.facade.IReportFacade;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportControllerImpl implements ReportController {

    private final IReportFacade reportFacade;

    public ReportControllerImpl(IReportFacade reportFacade) {
        this.reportFacade = reportFacade;
    }

    @Override
    @RateLimiter(name = "getReport")
    @GetMapping("/invoices/{invoiceId}/customers/{customerId}")
    public ResponseEntity<CustomerDataDTO> get(@PathVariable("invoiceId") Long invoiceId, @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(reportFacade.get(invoiceId, customerId));
    }
}
