package br.com.energysa.energysareport.integration.feign;

import br.com.energysa.energysareport.dto.CustomerDTO;
import feign.Headers;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customerClient", url = "${customer.api.url}")
@Headers({"x-app-id: {xAppId}", "x-app-secret: {xAppSec}"})
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    @Bulkhead(name = "customerApi")
    CustomerDTO getByCustomerId(@RequestHeader("xAppId") String appId, @RequestHeader("xAppSec") String appSecret, @PathVariable("id") Long customerId);
}
