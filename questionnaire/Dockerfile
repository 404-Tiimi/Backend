# Build-vaihe
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /opt/app

# Kopioi Mavenin asetukset ja projektin metadata
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Lataa riippuvuudet
RUN ./mvnw dependency:go-offline

# Kopioi lähdekoodi
COPY ./src ./src

# Buildaa projekti
RUN ./mvnw clean install -DskipTests

# Kopioi buildattu JAR-tiedosto suoraan
RUN cp target/*.jar /opt/app/app.jar

# Runtime-vaihe
FROM eclipse-temurin:25-jre

WORKDIR /opt/app

# Kopioi buildattu JAR-tiedosto
COPY --from=builder /opt/app/app.jar /opt/app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
