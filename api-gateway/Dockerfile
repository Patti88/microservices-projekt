FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.properties /application.properties
COPY src/main/resources/application-docker.properties /application-docker.properties
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]