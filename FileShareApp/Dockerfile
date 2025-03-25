
FROM jelastic/maven:3.9.5-openjdk-21

LABEL authors="Jayesh"

WORKDIR /app

CMD ls

COPY /target/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]
