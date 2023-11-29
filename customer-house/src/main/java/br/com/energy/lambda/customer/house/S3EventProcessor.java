package br.com.energy.lambda.customer.house;

import br.com.energy.lambda.customer.house.data.EnergyData;
import br.com.energy.lambda.customer.house.external.InvoiceEventQueue;
import br.com.energy.lambda.customer.house.external.dto.InvoiceDTO;
import br.com.energy.lambda.customer.house.s3.contract.CustomerData;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import org.jboss.logging.Logger;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Named("s3EventProcessor")
public class S3EventProcessor implements RequestHandler<S3Event, List<InvoiceDTO>> {
    private static final Logger LOG = Logger.getLogger(S3EventProcessor.class);
    private final InvoiceEventQueue invoiceEventQueue;
    private final S3Client s3Client;
    private final ObjectMapper objectMapper;

    public S3EventProcessor(InvoiceEventQueue invoiceEventQueue, S3Client s3Client, ObjectMapper objectMapper) {
        this.invoiceEventQueue = invoiceEventQueue;
        this.s3Client = s3Client;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<InvoiceDTO> handleRequest(S3Event s3Event, Context context) {
        LOG.infof("S3Event: %s", s3Event.getRecords().stream().filter(Objects::nonNull).map(r ->
                "Bucket: " + r.getS3().getBucket().getName() + " Key: " +r.getS3().getObject().getKey()).collect(Collectors.joining()));
        List<InvoiceDTO> invoices = new ArrayList<>();

        s3Event.getRecords().forEach(r -> {
            ResponseBytes<GetObjectResponse> responseResponseBytes = s3Client.getObjectAsBytes(GetObjectRequest.builder().bucket(r.getS3().getBucket().getName()).key(r.getS3().getObject().getKey()).build());
            CustomerData customerData;

            try {
                customerData = objectMapper.readValue(responseResponseBytes.asByteArray(), CustomerData.class);
                LOG.infof("Customer Data: %s", customerData);
            } catch (IOException e) {
                LOG.error("It wasn't possible to convert customer invoices", e);
                throw new RuntimeException(e);
            }

            if (customerData != null) {
                var consumptionData = List.of(new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)),
                        new EnergyData(ThreadLocalRandom.current().nextDouble(10, 1000), ThreadLocalRandom.current().nextInt(1, 8)));
                double kwhTotal = consumptionData.stream().mapToDouble(EnergyData::calculateKwh).sum();

                LOG.infof("Calling invoice client: %s", customerData);
                var invoiceData = new InvoiceDTO(customerData.id(), kwhTotal);
                invoiceEventQueue.sendToBeBillable(invoiceData);
                invoices.add(invoiceData);
            }
        });
        return invoices;
    }
}
