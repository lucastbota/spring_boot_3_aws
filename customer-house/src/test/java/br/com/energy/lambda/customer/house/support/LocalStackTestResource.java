package br.com.energy.lambda.customer.house.support;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;

public class LocalStackTestResource {/*implements QuarkusTestResourceLifecycleManager {
    private static final int S3_PORT = 4566;

    private LocalStackContainer localstack;

    @Override
    public Map<String, String> start() {
        localstack = new LocalStackContainer("2.3.0")
                .withExposedPorts(S3_PORT)
                .waitingFor(Wait.forSuccessfulCommand("awslocal s3api create-bucket --bucket wings-of-fire"))
                .withServices(
                        LocalStackContainer.Service.S3
                );

        localstack.start();

        return Map.of("quarkus.s3.endpoint-override", "http://" + localstack.getHost() + ":" + localstack.getMappedPort(S3_PORT));

    }

    @Override
    public void stop() {
        if (localstack != null) {
            localstack.stop();
        }
    }*/
}
