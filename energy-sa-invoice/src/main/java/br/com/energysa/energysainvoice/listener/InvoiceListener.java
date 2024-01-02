package br.com.energysa.energysainvoice.listener;

import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.service.InvoiceService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InvoiceListener {
    private static final Logger LOGGER = Logger.getLogger(InvoiceListener.class.getName());
    private final InvoiceService invoiceService;

    public InvoiceListener(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @SqsListener(queueNames = "${queue.url}")
    public void listen(InvoiceDTO dto) {
        LOGGER.info(String.format("Event Received: %s", dto));
        invoiceService.create(dto);
    }
}
