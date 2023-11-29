FROM amazoncorretto:21.0.0

VOLUME /tmp

ARG LOCALSTACK_HOST
ARG S3_ENDPOINT
ARG EXTRACTED=./target/extracted

ENV LOCALSTACK_HOST $LOCALSTACK_HOST
ENV S3_ENDPOINT $S3_ENDPOINT

COPY ${EXTRACTED}/dependencies/ ./
COPY ${EXTRACTED}/spring-boot-loader/ ./
COPY ${EXTRACTED}/snapshot-dependencies/ ./
COPY ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]