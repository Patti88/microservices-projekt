#!/bin/bash

# Bygg Authorization Server
cd auth-server
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=auth-server:latest
cd ..

# Bygg API Gateway
cd api-gateway
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=api-gateway:latest
cd ..

# Bygg Joke Service
cd joke-service
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=joke-service:latest
cd ..

# Bygg Quote Service
cd quote-service
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=quote-service:latest
cd ..