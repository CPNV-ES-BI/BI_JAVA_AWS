FROM openjdk:17-jdk-alpine as base

WORKDIR /app
COPY ./.mvn .mvn
COPY ./mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B
COPY ./src ./src
RUN ./mvnw compile

FROM base as test
COPY ./JacocoPrinter.groovy ./
CMD ["./mvnw", "test"]

FROM test as verify
CMD ["./mvnw", "verify"]

FROM base as development
CMD ["./mvnw", "spring-boot:run"]
