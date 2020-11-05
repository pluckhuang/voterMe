#!/usr/bin/env sh

# dump jacoco.exec

mkdir jacoco/report ; java -jar jacoco/lib/jacococli.jar dump --destfile jacoco/src/jacoco.exec
