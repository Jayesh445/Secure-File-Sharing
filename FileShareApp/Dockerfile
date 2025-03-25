
FROM openjdk:21

LABEL authors="Jayesh"

COPY target/app.jar app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar","--spring.profiles.active=prod"]
