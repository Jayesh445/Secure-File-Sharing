
FROM maven:3.9-eclipse-temurin-17 AS build

LABEL authors="Jayesh"

WORKDIR /app

COPY . .

CMD [ "mvn", "clean", "package", "-Pprod", "-DskipTests" ]

FROM openjdk:22-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=prod"]
