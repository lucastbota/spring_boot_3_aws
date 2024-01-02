package br.com.energysa.energysareport.facade.impl;

import br.com.energysa.energysareport.dto.CustomerDataDTO;
import br.com.energysa.energysareport.entity.InvoiceReport;
import br.com.energysa.energysareport.facade.IReportFacade;
import br.com.energysa.energysareport.service.ICustomerService;
import br.com.energysa.energysareport.service.IInvoiceService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Service
public class ReportFacadeImpl implements IReportFacade {
    private final IInvoiceService invoiceService;
    private final ICustomerService customerService;
    private final DynamoDbTemplate dynamoDbTemplate;

    public ReportFacadeImpl(IInvoiceService invoiceService, ICustomerService customerService, DynamoDbTemplate dynamoDbTemplate) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @Override
    public CustomerDataDTO get(Long invoiceId, Long customerId) {
        var cachedData = dynamoDbTemplate.load(Key.builder().partitionValue(invoiceId).build(), InvoiceReport.class);
        if (cachedData != null) {
            return new CustomerDataDTO(cachedData.getCustomerId(), cachedData.getName(), cachedData.getTotal(), cachedData.getLatitude(), cachedData.getLongitude());
        }
        //TODO deixar assincrono
        final var invoiceTotal = invoiceService.get(invoiceId);
        final var customerDTO = customerService.get(customerId);
        dynamoDbTemplate.save(InvoiceReport.of(invoiceId, customerId, customerDTO.name(), invoiceTotal, customerDTO.latitude(), customerDTO.longitude()));
        return new CustomerDataDTO(customerId, customerDTO.name(), invoiceTotal, customerDTO.latitude(), customerDTO.longitude());
    }
}
