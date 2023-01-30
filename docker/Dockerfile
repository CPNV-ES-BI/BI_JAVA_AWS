FROM openjdk:17-jdk-alpine as build

WORKDIR /app

COPY ./.mvn .mvn
COPY ./mvnw pom.xml ./
COPY ./src/main ./src/main

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests


FROM build as development
CMD ["./mvnw", "spring-boot:run"]


FROM build as test
COPY ./src/test ./src/test
CMD ["./mvnw", "test"]


FROM test as verify
CMD ["./mvnw", "verify"]
