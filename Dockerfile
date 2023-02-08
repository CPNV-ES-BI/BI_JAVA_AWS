ARG JAVA_VERSION=17
ARG APP_NAME=bi-java-aws
ARG HOME=/home/$APP_NAME
ARG WORKDIR=/home/$APP_NAME/app

#############
### Build ###
#############
FROM openjdk:${JAVA_VERSION}-jdk-alpine as build
ARG APP_NAME
ARG HOME
ARG WORKDIR
ENV mvn=./mvnw

WORKDIR $WORKDIR

VOLUME $HOME/.m2
# Spring Boot application creates working directories for Tomcat by default at /tmp.
VOLUME /tmp

COPY ./.mvn .mvn
COPY $mvn ./
RUN chmod +x $mvn

RUN addgroup -S -g 1000 $APP_NAME && \
    adduser -S -u 1000 -G $APP_NAME $APP_NAME && \
    chown -R $APP_NAME:$APP_NAME $WORKDIR

USER $APP_NAME

COPY pom.xml ./
RUN $mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies clean

COPY ./src/main ./src/main
RUN $mvn package -o -DskipTests

###################
### Producttion ###
###################
FROM eclipse-temurin:${JAVA_VERSION}-jre-alpine AS production
ARG WORKDIR

WORKDIR $WORKDIR

COPY --from=build $WORKDIR/target/$APP_NAME*.jar $APP_NAME.jar

###################
### Development ###
###################
FROM production AS development
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
