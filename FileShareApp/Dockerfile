
FROM maven:3.9

LABEL authors="Jayesh"

WORKDIR /app

COPY . /app/.

RUN mvn clean package -Pprod -DskipTests

FROM openjdk:21

WORKDIR /app

COPY /target/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]
