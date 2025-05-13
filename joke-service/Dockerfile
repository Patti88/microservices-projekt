FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/joke-service-0.0.1-SNAPSHOT.jar joke-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "joke-service.jar"]