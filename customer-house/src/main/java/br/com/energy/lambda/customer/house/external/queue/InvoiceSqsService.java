package br.com.energy.lambda.customer.house.external.queue;

import br.com.energy.lambda.customer.house.external.InvoiceEventQueue;
import br.com.energy.lambda.customer.house.external.dto.InvoiceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.sqs.SqsClient;

@ApplicationScoped
public class InvoiceSqsService implements InvoiceEventQueue {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    @ConfigProperty(name = "queue.url")
    private String queueUrl;

    public InvoiceSqsService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendToBeBillable(InvoiceDTO dto) {
        sqsClient.sendMessage(m -> m.queueUrl(queueUrl).messageBody(getJsonAsString(dto)));
    }

    private String getJsonAsString(InvoiceDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Não foi possível modificar o objeto", e);
        }
    }
}
