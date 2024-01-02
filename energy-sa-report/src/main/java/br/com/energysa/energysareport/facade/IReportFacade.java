package br.com.energysa.energysareport.facade;

import br.com.energysa.energysareport.dto.CustomerDataDTO;

public interface IReportFacade {
    CustomerDataDTO get(Long invoiceId, Long customerId);
}
