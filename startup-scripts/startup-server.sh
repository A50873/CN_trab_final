#!/bin/bash

export GOOGLE_APPLICATION_CREDENTIALS=/var/grpcserver/cn2526-t3-g07-trab-final.json

nohup java -jar /var/grpcserver/server.jar 7500 > /var/grpcserver/server.log 2>&1 &