# BI Java AWS

<div style="position: fixed; bottom: 0; right: 0; padding-right: 1em; padding-bottom: 2em;">
  <p align="right"><a href="#bi-java-aws">Back to top &#9650;</a></p>
</div>

* [BI Java AWS](#bi-java-aws)
    * [Introduction](#introduction)
    * [Requirements](#requirements)
    * [Docker](#docker)
    * [Local](#local)
    * [Usage](#usage)
    * [Test](#test)
    * [Contributing](#contributing)
    * [License](#license)

## Introduction

This is a Spring Boot microservice that can be run as a Docker container.
It is a simple REST API that returns 'Hello World'.

## Requirements

* Java 17
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
docker compose up development
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

[MIT](https://choosealicense.com/licenses/mit/)
