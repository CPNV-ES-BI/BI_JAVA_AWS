# Spring Boot Microservice Docker

## Introduction

This is a Spring Boot microservice that can be run as a Docker container.
It is a simple REST API that returns 'Hello World'.

## Docker

### Build

To build the Docker image, run the following command:

```bash
docker build -t spring-helloworld .
```

### Run

To run the Docker container, run the following command:

```bash
docker run -p 8080:8080 spring-helloworld
```

### Usage

To get the 'Hello World' message, run the following command:

```bash
curl http://localhost:8080
```
