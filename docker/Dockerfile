ARG SERVICE_NAME=bi-java-aws
ARG JAVA_VERSION=17
ARG WORKDIR=/app
ARG mvn=./mvnw


#############
### Build ###
#############
FROM openjdk:$JAVA_VERSION-jdk-alpine as build

ARG WORKDIR
ARG mvn

WORKDIR $WORKDIR

COPY ./.mvn .mvn
COPY $mvn ./
RUN chmod +x $mvn

# To always use the local repository and be able to run commands offline
RUN mkdir -p $WORKDIR/.m2/repository && echo \
    "<settings xmlns='http://maven.apache.org/SETTINGS/1.0.0\' \
    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' \
    xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd'> \
        <localRepository>$WORKDIR/.m2/repository</localRepository> \
    </settings>" \
    > $WORKDIR/.m2/settings.xml;

COPY pom.xml ./
RUN $mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies clean

COPY ./src/main ./src/main
RUN $mvn package -o -DskipTests


###################
### Producttion ###
###################
FROM eclipse-temurin:$JAVA_VERSION-jre-alpine AS production

ARG WORKDIR
ARG SERVICE_NAME
ENV SERVICE_NAME=$SERVICE_NAME

WORKDIR $WORKDIR

COPY --from=build $WORKDIR/target/$SERVICE_NAME*.jar $SERVICE_NAME.jar


###################
### Development ###
###################
FROM production AS development

ARG SERVICE_NAME
ENV SERVICE_NAME=$SERVICE_NAME

ENTRYPOINT java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005 -jar $SERVICE_NAME.jar


############
### Test ###
############
FROM build as test

ARG WORKDIR
ARG mvn
ENV mvn=$mvn

COPY --from=build $WORKDIR/.m2/repository $WORKDIR/.m2/repository

COPY ./src/test ./src/test

CMD $mvn test -o


##############
### Verify ###
##############
FROM test as verify

ARG WORKDIR
ARG mvn
ENV mvn=$mvn

COPY --from=build $WORKDIR/.m2/repository $WORKDIR/.m2/repository

CMD $mvn verify -o
