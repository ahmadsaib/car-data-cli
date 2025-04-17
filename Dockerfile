FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/car-data-cli-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

RUN mkdir -p /app/logs

ENTRYPOINT ["java", "-jar", "app.jar"]
