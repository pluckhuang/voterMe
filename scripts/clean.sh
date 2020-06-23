#!/usr/bin/env sh

# clean files

echo "clean files..."

rm out.log

rm -rf jacoco/src/
rm -rf jacoco/report/
rm -rf apitest/logs
rm -rf apitest/reports

fd __pycache__ . -x rm -rf

cd uitest && mvn clean && cd ..
