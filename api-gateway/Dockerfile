FROM openjdk:17-jdk-slim

VOLUME /tmp

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Behåll dessa om du vill, men de behövs inte längre
# COPY src/main/resources/application.properties /config/
# COPY src/main/resources/application-docker.properties /config/

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app.jar"]