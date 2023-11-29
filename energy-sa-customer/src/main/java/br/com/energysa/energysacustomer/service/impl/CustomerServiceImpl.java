package br.com.energysa.energysacustomer.service.impl;

import br.com.energysa.energysacustomer.aws.s3.contract.AddressData;
import br.com.energysa.energysacustomer.aws.s3.contract.CustomerData;
import br.com.energysa.energysacustomer.dto.CustomerDTO;
import br.com.energysa.energysacustomer.exceptions.IOBucketException;
import br.com.energysa.energysacustomer.service.CustomerService;
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
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Value("s3://customer-bureau/")
    private Resource s3Bucket;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CustomerData create(CustomerDTO customerDTO) {
        CustomerData data = new CustomerData(Instant.now().toEpochMilli(), customerDTO.name(), new AddressData(customerDTO.latitude(), customerDTO.longitude()));
        writeCustomerIntoBucket(data);
        return data;
    }

    @Override
    public CustomerData findById(Long id) {
        var content = getObject(Objects.toString(id));
        try {
            return objectMapper.readValue(content, CustomerData.class);
        } catch (JsonProcessingException e) {
            throw new ClassCastException();
        }
    }

    private String getObject(String key)   {
        try {
            return StreamUtils.copyToString(
                    s3Bucket.createRelative(key).getInputStream(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new IOBucketException();
        }
    }
    private void writeCustomerIntoBucket(CustomerData data) {
        try (OutputStream outputStream = ((S3Resource) s3Bucket.createRelative(Objects.toString(data.id()))).getOutputStream()) {
            outputStream.write(objectMapper.writeValueAsBytes(data));
        } catch (IOException e) {
            throw new IOBucketException();
        }
    }
}
