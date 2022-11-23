# Spring Boot Microservice Docker

## Table of Contents

<!-- TOC -->

* [Spring Boot Microservice Docker](#spring-boot-microservice-docker)
    * [Introduction](#introduction)
    * [Build](#build)
    * [Run](#run)
    * [Usage](#usage)
    * [Test](#test)

<!-- TOC -->

## Introduction

This is a Spring Boot microservice that can be run as a Docker container.
It is a simple REST API that returns 'Hello World'.

## Requirements

### Docker

* Docker

### Local

* Java 17
* Maven

## Build

To build the Docker image, run the following command:

```bash
docker build -t spring-helloworld .
```

## Run

To run the Docker container, run the following command:

```bash
docker run -p 8080:8080 spring-helloworld
```

## Usage

To get the 'Hello World' message, run the following command, or open the url in a browser.

```bash
curl http://localhost:8080
```

## Test

To run the tests in docker, run the following command:

```bash
docker run spring-helloworld ./mvnw test
```

To run the tests in local, run the following command:

```bash
./mvnw test
```
