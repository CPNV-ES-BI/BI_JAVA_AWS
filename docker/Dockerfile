ARG JAVA_VERSION=17
ARG WORKDIR=/app
ARG MVN=./mvnw


FROM openjdk:${JAVA_VERSION}-jdk-alpine as build

ARG WORKDIR
ARG MVN

WORKDIR ${WORKDIR}

COPY ./.mvn .mvn
COPY ${MVN} ./
RUN chmod +x ${MVN}

COPY pom.xml ./
RUN ${MVN} dependency:go-offline

COPY ./src/main ./src/main
RUN ${MVN} clean package -DskipTests


FROM eclipse-temurin:${JAVA_VERSION}-jre-alpine AS production

ARG WORKDIR
ENV MICROSERVICE_NAME=bi-java-aws

WORKDIR ${WORKDIR}

COPY --from=build ${WORKDIR}/target/${MICROSERVICE_NAME}*.jar app.jar


FROM production AS development

CMD ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]


FROM build as test
ARG MVN
ENV MVN=${MVN}

COPY ./src/test ./src/test
CMD ${MVN} test


FROM test as verify
ARG MVN
ENV MVN=${MVN}

CMD ${MVN} verify
