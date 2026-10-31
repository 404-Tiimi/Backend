#
# Mvn Build
#
FROM maven:3.9-eclipse-temurin-25 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Jar Package
#
FROM eclipse-temurin:25-jre
COPY --from=build /home/app/target/questionnaire-0.0.1-SNAPSHOT.jar /usr/local/lib/Questionnaire.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/Questionnaire.jar"]