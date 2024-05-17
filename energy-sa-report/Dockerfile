FROM amazoncorretto:21.0.0

VOLUME /tmp

EXPOSE  8094

ARG EXTRACTED=./target/energy-sa-report


COPY ${EXTRACTED}/dependencies/ ./
COPY ${EXTRACTED}/spring-boot-loader/ ./
COPY ${EXTRACTED}/snapshot-dependencies/ ./
COPY ${EXTRACTED}/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]