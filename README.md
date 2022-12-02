# Spring Boot Microservice Docker

## Table of Contents

<!-- TOC -->
* [Spring Boot Microservice Docker](#spring-boot-microservice-docker)
  * [Table of Contents](#table-of-contents)
  * [Introduction](#introduction)
  * [Requirements](#requirements)
  * [Docker](#docker)
  * [Local](#local)
  * [Usage](#usage)
  * [Test](#test)
  * [References](#references)
<!-- TOC -->

## Introduction

This is a Spring Boot microservice that can be run as a Docker container.
It is a simple REST API that returns 'Hello World'.

## Requirements

* Java 19
* Maven
* Docker
* Docker Compose

## Docker

To build the Docker image, run the following command:

```bash
./docker-build
```

To run the Docker container, run the following command:

```bash
docker compose up
```

## Local

To install the application in local, run the following command:

```bash
mvn clean package -DskipTests
```

To run the application in local, run the following command:

```bash
mvn spring-boot:run
```

## Usage

To get the 'Hello World' message, run the following command, or open the url in a browser.

```bash
curl http://localhost:8080
```

## Test

To run the tests in local, run the following command:

```bash
mvn test
```

---

## References

* [Docker - Kickstart Your Spring Boot Application Development](https://www.docker.com/blog/kickstart-your-spring-boot-application-development/)
