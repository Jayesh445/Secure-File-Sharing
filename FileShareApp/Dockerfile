
FROM openjdk:22-slim

LABEL authors="Jayesh"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=prod"]
