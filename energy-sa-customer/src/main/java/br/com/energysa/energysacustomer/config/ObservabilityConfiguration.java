package br.com.energysa.energysacustomer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

@Configuration
public class ObservabilityConfiguration {
    @Bean
    public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${otl.grpc.span.exporter.url}") String url) {
        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
    }
}
