#!/bin/bash

export GOOGLE_APPLICATION_CREDENTIALS=/var/grpcserver/cn2526-t3-g07-trab-final.json

nohup java -jar /var/grpcserver/labels.jar > /var/grpcserver/labels.log 2>&1 &