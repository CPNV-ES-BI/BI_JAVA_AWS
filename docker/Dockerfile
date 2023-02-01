ARG MICROSERVICE_NAME=bi-java-aws
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
RUN --mount=type=cache,target=/root/.m2 ${MVN} de.qaware.maven:go-offline-maven-plugin:resolve-dependencies clean

COPY ./src/main ./src/main
RUN --mount=type=cache,target=/root/.m2 ${MVN} package -o -DskipTests


FROM eclipse-temurin:${JAVA_VERSION}-jre-alpine AS production

ARG WORKDIR
ARG MICROSERVICE_NAME
ENV MICROSERVICE_NAME=${MICROSERVICE_NAME}

WORKDIR ${WORKDIR}

COPY --from=build ${WORKDIR}/target/${MICROSERVICE_NAME}*.jar ${MICROSERVICE_NAME}.jar


FROM production AS development

ARG MICROSERVICE_NAME
ENV MICROSERVICE_NAME=${MICROSERVICE_NAME}

CMD java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005 -jar ${MICROSERVICE_NAME}.jar


FROM build as test

ARG MVN
ENV MVN=${MVN}

COPY ./src/test ./src/test
CMD ${MVN} test


FROM test as verify

ARG MVN
ENV MVN=${MVN}

CMD ${MVN} verify
