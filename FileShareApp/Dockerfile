
FROM openjdk:22-slim

LABEL authors="Jayesh"

WORKDIR /app

COPY target/*.jar FileShareApp-0.0.1.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar","--spring.profiles.active=prod"]
