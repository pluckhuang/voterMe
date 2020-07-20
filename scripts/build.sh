#!/usr/bin/env sh

# build project with unit test skipped

echo "build project..."

./gradlew build -x test

cd uitest && mvn clean package && cd ..