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

#### aws.properties file

The application need some properties to access the AWS S3 service. They are required in all environments, either
locally, in a docker container or in the CI/CD pipeline.

There is already an `aws.properties` file that containing non-sensitive information :

| Property Name   | Description                               |
|-----------------|-------------------------------------------|
| AWS_REGION      | AWS region where the commands will be run |
| AWS_BUCKET_NAME | S3 bucket name to use inside the service  |

#### Sensitive

<span style="color:red">**NOTE:**</span> The file `aws.secrets.properties` containing sensitive information is missing
from the repository. It should be created manually and never be committed to the repository.

Create a new file named `aws.secrets.properties` in the `src/main/resources` folder.

```shell
# Bash
echo -e "AWS_ACCESS_KEY_ID=\nAWS_SECRET_ACCESS_KEY=" > src/main/resources/aws.secrets.properties
# Powershell
Set-Content -Path src/main/resources/aws.secrets.properties -Value "AWS_ACCESS_KEY_ID=", "AWS_SECRET_ACCESS_KEY="
```

Fill in the `aws.secret.properties` file with the following information:

| Property Name         | Description    |
|-----------------------|----------------|
| AWS_ACCESS_KEY_ID     | S3 key id      |
| AWS_SECRET_ACCESS_KEY | S3 secret name |

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

| Endpoint         | Description  |
|------------------|--------------|
| /actuator/health | Health check |

## Tests

_The coverage report is generated in the maven `test` phase, so everytime you run test(s), the report will be
generated in the `target/site/jacoco` folder and printed in the console._

Run the tests:

```bash
mvn clean test
```

Run a specific test:

```bash
mvn clean test -Dtest=TestClassName#methodName
```

Run the tests and check the code coverage:

```bash
mvn clean verify
```

With JaCoCo, the code coverage check is done in the `verify` phase. The threshold is set to 100% of instructions in the
`DataObjectImpl` class. If the coverage is lower than the threshold, this will fail.

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

A debug port `5005` is exposed using
the [JDWP](https://docs.oracle.com/javase/8/docs/technotes/guides/jpda/jdwp-spec.html) (Java Debug Wire Protocol)
protocol in the `development` image. You can connect to it using your IDE.

Run the tests:

```bash
docker compose up test --build
# or
make docker-up-test
```

Run a specific test:

```bash
docker-compose run --rm test ./mvnw test -Dtest=TestClassName#methodName
```

## Folder structure

See the [folder structure](doc/FOLDERS_STRUCTURE.md) documentation.

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
