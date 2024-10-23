# Basis-Image
FROM openjdk:17-jdk-alpine

# Arbeitsverzeichnis erstellen
WORKDIR /app

# Gradle Wrapper und Source Code kopieren
COPY . .

# Gradle build
RUN ./gradlew bootJar

# Container starten
ENTRYPOINT ["java", "-jar", "build/libs/leistungsnachweis-docker-0.0.1-SNAPSHOT.jar"]
