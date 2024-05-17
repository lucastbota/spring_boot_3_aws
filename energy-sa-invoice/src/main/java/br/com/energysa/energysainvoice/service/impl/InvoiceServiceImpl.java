package br.com.energysa.energysainvoice.service.impl;

import br.com.energysa.energysainvoice.aws.s3.contract.InvoiceData;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.exceptions.IOBucketException;
import br.com.energysa.energysainvoice.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final static Logger LOGGER = Logger.getLogger(InvoiceServiceImpl.class.getName());
    private static final String BUCKET_NAME = "watt-bureau";
    @Autowired
    private S3Client s3Client;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void create(InvoiceDTO dto) {
        var fiftyCents = 0.50f;
        var total = BigDecimal.valueOf(dto.kwh() * fiftyCents);
        var data = new InvoiceData(Instant.now().toEpochMilli(), dto.customerId(), dto.kwh(), total);
        LOGGER.log(Level.INFO, "Invoice: {0}", data);
        writeCustomerIntoBucket(data);
    }

    @Override
    public InvoiceData getInvoice(Long id) {
        var content = getObject(Objects.toString(id));
        try {
            return objectMapper.readValue(content, InvoiceData.class);
        } catch (JsonProcessingException e) {
            throw new ClassCastException();
        }
    }

    @Override
    public List<String> getInvoiceKeys() {
        var request = ListObjectsV2Request.builder().bucket(BUCKET_NAME).build();
        var response = s3Client.listObjectsV2(request);

        return response.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }

    private void writeCustomerIntoBucket(InvoiceData data) {
        try {
            s3Client.putObject(PutObjectRequest.builder().key(Objects.toString(data.id())).bucket(BUCKET_NAME).build(), RequestBody.fromBytes(objectMapper.writeValueAsBytes(data)));
        } catch (IOException e) {
            throw new IOBucketException();
        }
    }

    private String getObject(String key) {
        try {
            var s3Object = s3Client.getObject(GetObjectRequest.builder().bucket(BUCKET_NAME).key(key).build());
            return new String(s3Object.readAllBytes());
        } catch (IOException e) {
            throw new IOBucketException();
        }
    }
}
