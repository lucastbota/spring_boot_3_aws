package br.com.energysa.energysareport.service.remote;

import br.com.energysa.energysareport.dto.CustomerDTO;
import br.com.energysa.energysareport.integration.feign.CustomerClient;
import br.com.energysa.energysareport.service.ICustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomerRemoteService implements ICustomerService {
    private final CustomerClient customerClient;
    private final String customerApiId;
    private final String customerApiSecret;

    public CustomerRemoteService(CustomerClient customerClient,
                                 @Value("${customer.api.id}") String customerApiId,
                                 @Value("${customer.api.secret}") String customerApiSecret) {
        this.customerClient = customerClient;
        this.customerApiId = customerApiId;
        this.customerApiSecret = customerApiSecret;
    }

    @Override
    public CustomerDTO get(Long customerId) {
        return customerClient.getByCustomerId(customerApiId, customerApiSecret, customerId);
    }
}
