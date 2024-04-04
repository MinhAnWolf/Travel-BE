#!/bin/sh
pwd
java -version
./gradlew wrapper
./gradlew --v
pwd
./gradlew clean build
cd AppController || exit
pwd
chmod +x gradlew
./gradlew wrapper
./gradlew bootRun