
FROM openjdk:21

LABEL authors="Jayesh"

COPY /target/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]
