package br.com.energysa.energysareport.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfiguration {
    @Bean
    public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${otl.grpc.span.exporter.url}") String url) {
        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
    }
    @Bean
    public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
}
