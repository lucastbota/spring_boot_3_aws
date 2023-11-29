package br.com.energy.lambda.customer.house.support;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;

public class MinioTestResource{ /*implements QuarkusTestResourceLifecycleManager {
    private static final int S3_PORT = 9005;

    private GenericContainer<?> s3Container;

    @Override
    public Map<String, String> start() {
        s3Container = new GenericContainer<>("minio/minio:RELEASE.2023-11-11T08-14-41Z")
                .withExposedPorts(S3_PORT)
                .withEnv("MINIO_ROOT_USER", "accessKey")
                .withEnv("MINIO_ROOT_PASSWORD", "secretKey")
                .waitingFor(Wait.forLogMessage(".*1 Online.*", 1))
                .withCommand("server /data");

        s3Container.start();

        return Map.of("quarkus.s3.endpoint-override", "http://" + s3Container.getHost() + ":" + s3Container.getMappedPort(S3_PORT));
    }

    @Override
    public void stop() {
        if (s3Container != null) {
            s3Container.stop();
        }
    }*/
}
