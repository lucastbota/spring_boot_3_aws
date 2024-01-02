package br.com.energysa.energysainvoice.service.impl;

import br.com.energysa.energysainvoice.aws.s3.contract.InvoiceData;
import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.exceptions.IOBucketException;
import br.com.energysa.energysainvoice.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.S3Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final static Logger LOGGER = Logger.getLogger(InvoiceServiceImpl.class.getName());
    @Value("s3://watt-bureau/")
    private Resource s3Bucket;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void create(InvoiceDTO dto) {
        var fiftyCents = 0.50f;
        var total = BigDecimal.valueOf(dto.kwh() * fiftyCents);
        var data = new InvoiceData(Instant.now().toEpochMilli(),dto.customerId(), dto.kwh(), total);
        LOGGER.log(Level.INFO, "Invoice: {0}", data);
        writeCustomerIntoBucket(data);
    }

    @Override
    public BigDecimal getTotal(Long id) {
        var content = getObject(Objects.toString(id));
        try {
            return objectMapper.readValue(content, InvoiceData.class).total();
        } catch (JsonProcessingException e) {
            throw new ClassCastException();
        }
    }

    private void writeCustomerIntoBucket(InvoiceData data) {
        try (OutputStream outputStream = ((S3Resource) s3Bucket.createRelative(Objects.toString(data.id()))).getOutputStream()) {
            outputStream.write(objectMapper.writeValueAsBytes(data));
        } catch (IOException e) {
            throw new IOBucketException();
        }
    }

    private String getObject(String key) {
        try {
            return StreamUtils.copyToString(
                    s3Bucket.createRelative(key).getInputStream(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new IOBucketException();
        }
    }
}
