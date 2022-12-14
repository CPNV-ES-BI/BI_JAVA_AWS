# BI Java AWS

## Table of Contents

* [BI Java AWS](#bi-java-aws)
    * [Table of Contents](#table-of-contents)
    * [Introduction](#introduction)
    * [Requirements](#requirements)
    * [Configuration](#configuration)
        * [AWS properties](#aws-properties)
    * [Docker](#docker)
    * [Local](#local)
    * [Usage](#usage)

## Introduction

This is a Spring Boot microservice that can be run as a Docker container.
It is a simple REST API that returns 'Hello World'.

## Requirements

| Requirement     | Version  | Link                                                                                                                                                               |
|-----------------|----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Java            | 17       | [Link](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)                                                                             |
| Maven           | 3.8.6    | [Link](https://maven.apache.org/download.cgi)                                                                                                                      |
| Docker          | 20.10.17 | [Link](https://docs.docker.com/engine/install/)                                                                                                                    |
| Docker Compose  | 1.29.2   | [Link](https://docs.docker.com/compose/install/) - Docker Desktop includes Docker Compose along with Docker Engine and Docker CLI which are Compose prerequisites. |
| Make (optional) | 4.3      | There is a lot of ways to install Make, according to different OS. Check on Google the specific one for your OS (reason why it's optional)                         |

## Configuration

The configuration is done through properties files. They are located in the `src/main/resources` folder.

### AWS properties

Copy the `aws.example.properties` file and rename it to `aws.properties`.

<span style="color:red">**NOTE:**</span> The `aws.properties` contains sensitive information and should not be committed
to the repository.

| Property Name       | Description                                                |
|---------------------|------------------------------------------------------------|
| aws.region          | The AWS region                                             |
| aws.accessKeyId     | The S3 key name where the application will be deployed.    |
| aws.secretAccessKey | The S3 secret name where the application will be deployed. |

## Docker

To build and run the Docker container, run the following command:

```bash
docker compose up development --build
# or
make docker-up
```

To build and test the Docker container, run the following command:

```bash
docker compose up test --build
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

To run a specific test, run the following command:

```bash
mvn test -Dtest=TestClassName#methodName
```

## Usage

To get the 'Hello World' message, run the following command, or open the url in a browser.

```bash
curl http://localhost:8080
```
