package br.com.energysa.energysareport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EnergySaReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergySaReportApplication.class, args);
	}

}
