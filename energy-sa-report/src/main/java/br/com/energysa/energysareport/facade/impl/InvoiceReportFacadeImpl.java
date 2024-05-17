package br.com.energysa.energysareport.facade.impl;

import br.com.energysa.energysareport.dto.CustomerDTO;
import br.com.energysa.energysareport.dto.CustomerDataDTO;
import br.com.energysa.energysareport.dto.InvoiceDTO;
import br.com.energysa.energysareport.entity.InvoiceReport;
import br.com.energysa.energysareport.exceptions.ConcurrentException;
import br.com.energysa.energysareport.facade.IReportFacade;
import br.com.energysa.energysareport.service.ICustomerService;
import br.com.energysa.energysareport.service.IInvoiceService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class InvoiceReportFacadeImpl implements IReportFacade {
    private final IInvoiceService invoiceService;
    private final ICustomerService customerService;
    private final DynamoDbTemplate dynamoDbTemplate;

    public InvoiceReportFacadeImpl(IInvoiceService invoiceService, ICustomerService customerService, DynamoDbTemplate dynamoDbTemplate) {
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

        CompletableFuture<InvoiceDTO> futureInvoice = invoiceService.getInvoiceById(invoiceId);
        CompletableFuture<CustomerDTO> futureCustomer =  customerService.getCustomerById(customerId);
        CompletableFuture.allOf(futureInvoice, futureCustomer).join();

        final InvoiceDTO invoiceDTO;
        final CustomerDTO customerDTO;
        try {
            customerDTO = futureCustomer.get();
            invoiceDTO = futureInvoice.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ConcurrentException(e);
        }
        dynamoDbTemplate.save(InvoiceReport.of(invoiceId, customerId, customerDTO.name(), invoiceDTO.total(), customerDTO.latitude(), customerDTO.longitude()));
        return new CustomerDataDTO(customerId, customerDTO.name(), invoiceDTO.total(), customerDTO.latitude(), customerDTO.longitude());
    }

    @Override
    public List<CustomerDataDTO> getAllCustomers() {
        var invoices = dynamoDbTemplate.scanAll(InvoiceReport.class);
        return invoices.items().stream().filter(Objects::nonNull).map(i -> new CustomerDataDTO(i.getInvoiceId(), i.getName(), i.getTotal(), i.getLatitude(), i.getLongitude())).collect(Collectors.toList());
    }

}
