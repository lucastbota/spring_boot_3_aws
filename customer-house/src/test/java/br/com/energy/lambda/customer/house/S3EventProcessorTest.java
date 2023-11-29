package br.com.energy.lambda.customer.house;

import br.com.energy.lambda.customer.house.external.InvoiceEventQueue;
import br.com.energy.lambda.customer.house.external.dto.InvoiceDTO;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class S3EventProcessorTest {
    @Inject
    private S3EventProcessor s3EventProcessor;
    @InjectMock
    private InvoiceEventQueue invoiceEventQueue;
    @InjectMock
    private S3Client s3Client;

    @Test
    public void processTest() throws JsonProcessingException {
        String jsonContent = getJsonContent();
        S3Event s3Event = new S3Event(List.of(getS3EventNotificationRecord(jsonContent)));

        Mockito.when(s3Client.getObjectAsBytes(Mockito.any(GetObjectRequest.class))).thenReturn(ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), getJsonContent().getBytes(StandardCharsets.UTF_8)));


        var lambdaResult = s3EventProcessor.handleRequest(s3Event, null);
        assertTrue(lambdaResult.stream().allMatch(d -> d.customerId().equals(1699203015888L)));
    }


    @NotNull
    private static S3EventNotification.S3EventNotificationRecord getS3EventNotificationRecord(String jsonContent) {
        return new S3EventNotification.S3EventNotificationRecord("sa-east-1",
                "criação objeto",
                "trigger",
                "2023-11-11",
                "1.0",
                null,
                null,
                new S3EventNotification.S3Entity("foo",
                        new S3EventNotification.S3BucketEntity("customers", null, null),
                        new S3EventNotification.S3ObjectEntity("customer-xpto", Integer.valueOf(jsonContent.length()).longValue(), null, null, null), null),
                null
        );
    }

    private static String getJsonContent() {
        return """
                {
                "id": 1699203015888,
                "name": "Dr. Ribeirinho",
                "addressData": {
                  "latitude": "-23.5489",
                  "longitude": "-46.6388"
                 }
                }""";
    }
}
