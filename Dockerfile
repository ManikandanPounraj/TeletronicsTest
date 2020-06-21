# Alpine because it's lighter
FROM openjdk:8-jdk-alpine

MAINTAINER Teletronics <test@teletronics.com>

ADD target/teletroics-test-ms-1.0.0-SNAPSHOT.jar teletroics-test-ms-1.0.0-SNAPSHOT.jar
ADD target/classes/application.yml application.yml

RUN addgroup -g 2000 -S appuser && adduser -u 2000 -S appuser -G appuser
RUN mkdir /logs && chown -R appuser:appuser /logs teletroics-test-ms-1.0.0-SNAPSHOT.jar application.yml
USER appuser
ENTRYPOINT ["nohup", "java", "-jar", "/teletroics-test-ms-1.0.0-SNAPSHOT.jar", "&"]

CMD [ "--server.port=${SERVICE_PORT:8080}", "--spring.profiles.active=${SERVICE_ACTIVE_PROFILE:dev}", "--spring.config.location=file:///application.yml,file://${SERVICE_CONFIG_FILE:}"]

EXPOSE ${SERVICE_SERVER_PORT}