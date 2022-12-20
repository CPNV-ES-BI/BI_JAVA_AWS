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

| Property Name       | Description                          |
|---------------------|--------------------------------------|
| aws.region          | AWS region                           |
| aws.accessKeyId     | S3 key id                            |
| aws.secretAccessKey | S3 secret name                       |
| aws.bucketName      | S3 bucket name to use in the service |

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

To install the dependencies, run the following command:

```bash
mvn dependency:resolve
```

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

## Test

To run the tests in local, run the following command:

```bash
mvn test
```

## Contributing

We welcome contributions to this project! If you have an idea for a new feature or have found a bug, please open
an issue on GitHub to let us know.

If you would like to contribute code to the project, please follow these steps:

1. Clone the repository to your local machine
2. Create a new branch for your feature using `git flow feature start <feature-name>`
3. Write and test your code
4. Update the documentation as necessary
5. Submit a pull request. Any pull request that does not pass the CI/CD pipeline or without new tests will be rejected.

We will review your pull request and discuss any necessary changes before merging it.

Thank you for considering contributing to this project!

## License

Distribution is permitted under the terms of the [MIT License](LICENSE).

## Contact

### Authors

- [Yannick Baudraz](https://github.com/yannickcpnv)
- [Anthony Bouillant](https://github.com/antbou)

### Reviewers

- [Nicolas Glassey](https://github.com/NicolasGlassey)
