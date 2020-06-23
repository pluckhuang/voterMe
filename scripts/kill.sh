#!/usr/bin/env sh

# close javaagent
PORT=8080

echo "close server..."
lsof -i :$PORT | grep java | awk {'print $2'} | xargs kill -9 $1
