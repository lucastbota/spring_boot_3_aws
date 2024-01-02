package br.com.energysa.energysareport.service.remote;

import br.com.energysa.energysareport.integration.feign.InvoiceClient;
import br.com.energysa.energysareport.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvoiceRemoteService implements IInvoiceService {
    private final InvoiceClient invoiceClient;
    private final String invoiceApiId;
    private final String invoiceApiSecret;

    public InvoiceRemoteService(InvoiceClient invoiceClient,
                                @Value("${invoice.api.id}")String invoiceApiId,
                                @Value("${invoice.api.secret}") String invoiceApiSecret) {
        this.invoiceClient = invoiceClient;
        this.invoiceApiId = invoiceApiId;
        this.invoiceApiSecret = invoiceApiSecret;
    }

    @Override
    public BigDecimal get(Long invoiceId) {
        return invoiceClient.getTotalByCustomerId(invoiceApiId, invoiceApiSecret, invoiceId);
    }
}
