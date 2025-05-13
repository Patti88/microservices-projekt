FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/quote-service-0.0.1-SNAPSHOT.jar quote-service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "quote-service.jar"]