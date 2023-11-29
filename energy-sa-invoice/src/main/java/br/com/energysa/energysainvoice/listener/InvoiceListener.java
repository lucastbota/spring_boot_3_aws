package br.com.energysa.energysainvoice.listener;

import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.service.InvoiceService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import org.springframework.stereotype.Component;

@Component
public class InvoiceListener {
    private final InvoiceService invoiceService;

    public InvoiceListener(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @SqsListener(queueNames = "${queue.url}")
    public void listen(InvoiceDTO dto) {
        invoiceService.create(dto);
    }
}
