#!/usr/bin/env sh

# start javaagent

SERVER_PORT=8080

make kill

JVM_PARAM="-Xms2g -Xmx2g -Xss1024k -XX:+UseG1GC -XX:ReservedCodeCacheSize=256m -XX:GCTimeRatio=19 -XX:MaxGCPauseMillis=100"

java -javaagent:jacoco/lib/jacocoagent.jar=output=tcpserver $JVM_PARAM -jar build/libs/*.jar --server.port=$SERVER_PORT > out.log &

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

