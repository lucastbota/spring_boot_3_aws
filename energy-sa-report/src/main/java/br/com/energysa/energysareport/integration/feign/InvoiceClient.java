package br.com.energysa.energysareport.integration.feign;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(name="invoiceClient", url = "${invoice.api.url}")
@Headers({"x-app-id: {xAppId}", "x-app-secret: {xAppSec}"})
public interface InvoiceClient {
    @GetMapping("/invoices/{id}")
    BigDecimal getTotalByCustomerId(@RequestHeader("xAppId") String appId, @RequestHeader("xAppSec") String appSecret, @PathVariable("id") Long customerId);
}
