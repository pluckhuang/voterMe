#!/usr/bin/env sh

# generate report upon jacoco.exec

# exclude unittest files <- classfiles
java -jar jacoco/lib/jacococli.jar report --classfiles build/classes/java/main --sourcefiles src/main/java/ --html jacoco/report jacoco/src/jacoco.exec
