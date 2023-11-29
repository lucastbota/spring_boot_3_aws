package br.com.energy.lambda.customer.house.external;

import br.com.energy.lambda.customer.house.external.dto.InvoiceDTO;

public interface InvoiceEventQueue {
    void sendToBeBillable(InvoiceDTO dto);
}
