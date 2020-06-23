#!/usr/bin/env sh

# close javaagent

echo "close server..."
lsof -i :8083 | grep java | awk {'print $2'} | xargs kill $1
