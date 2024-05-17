package br.com.energysa.energysareport.facade;

import br.com.energysa.energysareport.dto.CustomerDataDTO;

import java.util.List;

public interface IReportFacade {
    CustomerDataDTO get(Long invoiceId, Long customerId);

    /**Método com fins puramente debugatícios*/
    List<CustomerDataDTO> getAllCustomers();
}
