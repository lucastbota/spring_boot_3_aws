package br.com.energysa.energysainvoice.sqs;

import br.com.energysa.energysainvoice.dto.InvoiceDTO;
import br.com.energysa.energysainvoice.listener.InvoiceListener;
import io.awspring.cloud.sqs.config.SqsBootstrapConfiguration;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.endpoints.SqsEndpointProvider;

import java.net.URI;
import java.time.Duration;

@Import(SqsBootstrapConfiguration.class)
@Configuration
public class SqsConfig {
/*@Value("${aws.access.key}")
    private String accessKey;
    @Value("${aws.secret.access}")
    private String accessSecret;
    @Value("${aws.endpoint}")
    private String endpoint;*/


    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(opt -> opt.acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
                        .acknowledgementInterval(Duration.ofSeconds(3))
                        .acknowledgementThreshold(5)
                        .acknowledgementOrdering(AcknowledgementOrdering.ORDERED))
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }
 /*
    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test")))
                .region(Region.SA_EAST_1)
                .endpointOverride(URI.create(""))
                .build();
    }*/
}
