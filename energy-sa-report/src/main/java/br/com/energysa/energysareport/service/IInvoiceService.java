package br.com.energysa.energysareport.service;

import br.com.energysa.energysareport.dto.InvoiceDTO;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public interface IInvoiceService {

    CompletableFuture<InvoiceDTO> getInvoiceById(Long invoiceId);
}
