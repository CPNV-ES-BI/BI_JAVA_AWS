help:
	@grep -E '^[a-zA-Z_-]+:.*?# .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?# "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

build: # (Maven) Build spring boot application
	mvn clean package -DskipTests
spring-boot: # (Maven) Run spring boot application in development mode
	mvn clean spring-boot:run
test: # (Maven) Run tests
	mvn clean test
verify: # (Maven) Run tests and verify test coverage
	mvn clean verify
docker-dev: # (Docker Compose) Run spring boot application in development mode
	docker compose up development --build
docker-test:  # (Docker Compose) Run tests
	docker compose up test --build
docker-verify:  # (Docker Compose) Run tests and verify test coverage
	docker compose up verify --build
