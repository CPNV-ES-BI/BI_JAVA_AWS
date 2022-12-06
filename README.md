# BI Java AWS

## Table of Contents

<!-- TOC -->

* [BI Java AWS](#bi-java-aws)
    * [Table of Contents](#table-of-contents)
    * [Introduction](#introduction)
    * [Requirements](#requirements)
    * [Docker](#docker)
    * [Local](#local)
    * [Usage](#usage)

<!-- TOC -->

## Introduction

This is a Spring Boot microservice that can be run as a Docker container.
It is a simple REST API that returns 'Hello World'.

## Requirements

| Requirement    | Version  | Link                                                                                                                                                               |
|----------------|----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Java           | 17       | [Link](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)                                                                             |
| Maven          | 3.8.6    | [Link](https://maven.apache.org/download.cgi)                                                                                                                      |
| Docker         | 20.10.17 | [Link](https://docs.docker.com/engine/install/)                                                                                                                    |
| Docker Compose | 1.29.2   | [Link](https://docs.docker.com/compose/install/) - Docker Desktop includes Docker Compose along with Docker Engine and Docker CLI which are Compose prerequisites. |
| Make           | 4.3      | There is a lot of ways to install Make, according to different OS. Check on Google the specific one for your OS (reason why it's optional                          |

## Docker

To run the Docker container, run the following command:

```bash
docker compose up development
# or
make docker-up
```

To test the Docker container, run the following command:

```bash
docker compose up test
# or
make docker-up-test
```

## Local

To run the application in local, run the following command:

```bash
mvn spring-boot:run
```

To run the tests in local, run the following command:

```bash
mvn test
```

## Usage

To get the 'Hello World' message, run the following command, or open the url in a browser.

```bash
curl http://localhost:8080
```
