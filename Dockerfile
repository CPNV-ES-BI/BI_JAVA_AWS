ARG JAVA_VERSION=17
ARG APP_NAME=bi-java-aws
ARG HOME=/home/$APP_NAME
ARG WORKDIR=/home/$APP_NAME/app

#############
### Build ###
#############
FROM openjdk:${JAVA_VERSION}-jdk-alpine as build
ENV mvn=./mvnw

ARG WORKDIR
WORKDIR $WORKDIR

COPY ./.mvn .mvn
COPY $mvn ./

COPY pom.xml ./
ARG HOME
RUN --mount=type=cache,target=$HOME/.m2 \
    $mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies clean

COPY ./src/main ./src/main
RUN $mvn package -o -DskipTests

###################
### Producttion ###
###################
FROM eclipse-temurin:${JAVA_VERSION}-jre-alpine AS production

ARG WORKDIR
WORKDIR $WORKDIR

ARG APP_NAME
COPY --from=build $WORKDIR/target/$APP_NAME*.jar $APP_NAME.jar

###################
### Development ###
###################
FROM production AS development
ARG APP_NAME
ENV APP_NAME=$APP_NAME

RUN addgroup -S -g 1000 $APP_NAME && \
    adduser -S -u 1000 -G $APP_NAME $APP_NAME

USER $APP_NAME

# Spring Boot application creates working directories for Tomcat by default at /tmp.
VOLUME /tmp

ENTRYPOINT java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005 -jar $APP_NAME.jar

############
### Test ###
############
FROM build as test
COPY ./src/test ./src/test
CMD $mvn test -o

##############
### Verify ###
##############
FROM test as verify
CMD $mvn verify -o
