
FROM openjdk:22-slim

LABEL authors="Jayesh"

WORKDIR /app

COPY target/app.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar","--spring.profiles.active=prod"]
