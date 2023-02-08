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

| Property Name         | Description                              | Example Value                            |
|-----------------------|------------------------------------------|------------------------------------------|
| AWS_REGION            | AWS region                               | us-east-1                                |
| AWS_ACCESS_KEY_ID     | S3 key id                                | AKIAIOSFODNN7EXAMPLE                     |
| AWS_SECRET_ACCESS_KEY | S3 secret name                           | wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY |
| AWS_BUCKET_NAME       | S3 bucket name to use inside the service | my-bucket                                |

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

All available endpoints (api not included):

| Endpoint                   | Description                   | Method |
|----------------------------|-------------------------------|--------|
| /swagger-ui/               | Swagger UI                    | GET    |
| /v3/api-docs               | Swagger JSON                  | GET    |
| /actuator/health           | Health check                  | GET    |

The API documentation is available from swagger `http://localhost:8080/swagger-ui/`.

### File name with a slash

For any filename containing a slash, you have to encode the url that contains the slash to avoid that the router interprets the slash as the name of a route.

For example `dir/filename` becomes `dir%2Filename`
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
