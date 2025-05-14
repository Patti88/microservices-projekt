# Använd en bas-image med Java (OpenJDK 17 rekommenderas)
FROM openjdk:17-jdk-slim

# Skapa en volym för /tmp så applikationen kan skriva temporära filer
VOLUME /tmp

# Ange namnet på JAR-filen som ska kopieras (detta kommer att skapas av Maven)
ARG JAR_FILE=target/*.jar

# Kopiera den byggda JAR-filen från host-maskinen in i containern
COPY ${JAR_FILE} app.jar

# Ange kommandot för att köra applikationen när containern startas
ENTRYPOINT ["java","-jar","/app.jar"]

