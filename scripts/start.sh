#!/usr/bin/env sh

# start javaagent

SERVER_PORT=8083

make kill

java -javaagent:jacoco/lib/jacocoagent.jar=output=tcpserver -jar build/libs/*.jar --server.port=$SERVER_PORT > out.log &

actions() {
    curl "http://localhost:$SERVER_PORT" >> /dev/null 2>&1
}

actions
while [ $? -ne 0 ]
do
    sleep 5
    echo "wait server to start..."
    actions
done

echo "server connected.."

