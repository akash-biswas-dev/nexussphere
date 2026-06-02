

# Config to generate code using buf.
data-processor-gen-code:
	rm -rf services/data-processor/gen
	buf generate --template services/data-processor/buf.gen.yaml

# Configurations related to test workflows.
act_cmd=act -P ubuntu-latest=catthehacker/ubuntu:act-latest --bind --env GITHUB_REF_NAME=dev --container-architecture linux/arm64 --secret-file .secrets -W

core-workflow:
	 $(act_cmd) .github/workflows/core.yml

gateway-workflow:
	 $(act_cmd) .github/workflows/gateway.yml

web-workflow:
	 $(act_cmd) .github/workflows/web.yml

# Docker hub username
APP_NAME=biswasakash/nexussphere

# Command
DOCKER_BUILD_CMD=docker build --platform linux/amd64,linux/arm64 -t $(APP_NAME)
DOCKER_TAG_CMD=docker tag $(APP_NAME)

core-build:
	$(DOCKER_BUILD_CMD)-workspaces:local -f services/core/Dockerfile .

gateway-build:
	$(DOCKER_BUILD_CMD)-gateway:local -f services/gateway/Dockerfile .

web-build:
	$(DOCKER_BUILD_CMD)-web:local -f web/Dockerfile .


build-all: core-build gateway-build web-build 


DOCKER_PUSH_CMD=docker push $(APP_NAME)

# Container registry push config
core-push:
	$(DOCKER_PUSH_CMD)-core:local

gateway-push:
	$(DOCKER_PUSH_CMD)-gateway:local

web-push:
	$(DOCKER_PUSH_CMD)-web:local

push-all: core-push gateway-push web-push

# Delete containers config.
core-delete:
	docker rmi -f $(APP_NAME)-core:local

gateway-delete:
	docker rmi -f $(APP_NAME)-gateway:local

web-delete:
	docker rmi -f $(APP_NAME)-web:local

delete-all: core-delete gateway-delete web-delete


