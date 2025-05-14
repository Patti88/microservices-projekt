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

# (Valfritt) Exponera den port som applikationen lyssnar på.
# Du kan kommentera ut dessa om du inte vill exponera dem direkt
# utanför Docker-nätverket (vilket är vanligt när man använder en API Gateway).
# Om du vill testa tjänsterna direkt via deras egna portar kan du inkludera dem.
#
# För Authorization Server (port 8080):
# EXPOSE 8080
#
# För API Gateway (port 8081):
EXPOSE 8081
#
# För Joke Service (port 8082):
# EXPOSE 8082
#
# För Quote Service (port 8083):
# EXPOSE 8083