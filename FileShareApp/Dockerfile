
FROM jelastic/maven:3.9.5-openjdk-21

WORKDIR /app

COPY pom.xml ./app/pom.xml

RUN mvn dependency:go-offline

COPY src ./app/src

RUN mvn clean package -Pprod -DskipTests

LABEL authors="Jayesh"

COPY /target/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]
