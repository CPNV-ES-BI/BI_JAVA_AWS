FROM openjdk:17-jdk-alpine as build

WORKDIR /app

COPY ./.mvn .mvn
COPY ./mvnw ./
RUN chmod +x ./mvnw

COPY pom.xml ./
RUN ./mvnw dependency:go-offline

COPY ./src/main ./src/main
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine AS production

WORKDIR /app

COPY --from=build /app/target/bi-java-aws*.jar app.jar


FROM production AS development

CMD ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]


FROM build as test

COPY ./src/test ./src/test

CMD ["./mvnw", "test"]


FROM test as verify

CMD ["./mvnw", "verify"]
