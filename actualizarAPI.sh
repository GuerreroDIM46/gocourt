#!/bin/bash

docker stop gocourt-api
docker rm gocourt-api
git pull
gradle build
docker build -t gocourt-api .
docker compose up -d
