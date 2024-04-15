#!/bin/bash

docker stop gocourt-vue
docker rm gocourt-vue
git pull
cd frontend
docker build -t gocourt-vue-img .
docker compose up -d
