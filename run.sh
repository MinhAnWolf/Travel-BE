#!/bin/sh
java -version
./gradlew wrapper
./gradlew --v
./gradlew clean build
chmod +x gradlew
./gradlew wrapper
./gradlew bootRun