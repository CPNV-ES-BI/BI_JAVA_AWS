# BI Java AWS

* [BI Java AWS](#bi-java-aws)
    * [Introduction](#introduction)
    * [Requirements](#requirements)
    * [Configuration](#configuration)
        * [AWS properties](#aws-properties)
    * [Installation](#installation)
    * [Usage](#usage)
    * [Tests](#tests)
    * [Docker](#docker)
    * [Contributing](#contributing)
    * [License](#license)
    * [Contact](#contact)
        * [Authors](#authors)
        * [Reviewers](#reviewers)

## Introduction

This is a Spring Boot microservice whose objective is to implement [AWS S3](https://aws.amazon.com/s3/) as a data
source, in order to perform various techniques related to Business Intelligence.

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

```bash
cp src/main/resources/aws.example.properties src/main/resources/aws.properties
```

<span style="color:red">**NOTE:**</span> The `aws.properties` contains sensitive information and should not be committed
to the repository.

Fill in the `aws.properties` file with the following information:

| Property Name       | Description                              | Example Value                            |
|---------------------|------------------------------------------|------------------------------------------|
| aws.region          | AWS region                               | us-east-1                                |
| aws.accessKeyId     | S3 key id                                | AKIAIOSFODNN7EXAMPLE                     |
| aws.secretAccessKey | S3 secret name                           | wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY |
| aws.bucketName      | S3 bucket name to use inside the service | my-bucket                                |

## Installation

Install dependencies:

```bash
mvn dependency:resolve
```

Build the project:

```bash
# Skip tests to speed up the build
mvn clean package -DskipTests
```

Run the project:

```bash
mvn spring-boot:run
```

## Usage

The base url is `http://localhost:8080`.

All available endpoints:

| Endpoint         | Description                                                        |
|------------------|--------------------------------------------------------------------|
| /actuator/health | Health check                                                       |

## Tests

Run the tests:

```bash
mvn clean test
```

Run a specific test:

```bash
mvn clean test -Dtest=TestClassName#methodName
```

[//]: # (TODO : add test coverage section)

## Docker

You can also run the project/tests using Docker.

Build the Docker image:

```bash
docker compose build development
```

Run the docker image:

```bash
docker compose up development
```

Run the docker image and build in the same time:

```bash
docker compose up development --build
# or
make docker-up
```

Run the tests:

```bash
docker compose up test --build
# or
make docker-up-test
```

## Folder structure

See the [folder structure](doc/FOLDER_STRUCTURE.md) documentation.

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

Distribution is permitted under the terms of the [MIT License](LICENSE.md).

## Contact

### Authors

- [Yannick Baudraz](https://github.com/yannickcpnv)
- [Anthony Bouillant](https://github.com/antbou)

### Reviewers

- [Nicolas Glassey](https://github.com/NicolasGlassey)
