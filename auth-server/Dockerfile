FROM openjdk:17-jdk-slim

WORKDIR /app

# Kopiera JAR-filen (byt ut mot ditt exakta filnamn)
COPY target/auth-server-0.0.1-SNAPSHOT.jar auth-server.jar

# Exponera porten
EXPOSE 8080

# Startkommando
ENTRYPOINT ["java", "-jar", "auth-server.jar"]