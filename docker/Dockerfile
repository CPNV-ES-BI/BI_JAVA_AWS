FROM openjdk:17-jdk-alpine as build

WORKDIR /app

COPY ./.mvn .mvn
COPY ./mvnw pom.xml ./
COPY ./src/main ./src/main

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine AS production

WORKDIR /app

COPY --from=build /app/target/bi-java-aws-0.0.1-SNAPSHOT.jar app.jar


FROM production AS development

CMD ["java", "-jar", "app.jar"]


FROM build as test

COPY ./src/test ./src/test

CMD ["./mvnw", "test"]


FROM test as verify

CMD ["./mvnw", "verify"]
