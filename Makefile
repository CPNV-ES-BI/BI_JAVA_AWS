help:
	@grep -E '^[a-zA-Z_-]+:.*?# .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?# "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

docker-up: # (Docker Compose) Run spring boot application in development mode
	docker compose up development
docker-up-test:  # (Docker Compose) Run tests
	docker compose up test