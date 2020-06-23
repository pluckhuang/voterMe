#!/usr/bin/env sh

# dump jacoco.exec

java -jar jacoco/lib/jacococli.jar dump --destfile jacoco/src/jacoco.exec
