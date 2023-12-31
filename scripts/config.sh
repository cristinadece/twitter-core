#!/usr/bin/env bash

VERSION="0.0.1-SNAPSHOT"
XMX="-Xmx2000m"
#LOG=INFO
PROJECT_NAME=twitter
LOG=INFO
LOGAT=1000
CLI=it.cnr.isti.hpc.twitter.cli
E_BADARGS=65
HTML_LOG=/data/tweets/logs/full-dump-log.html
LOG_DIR=/data/tweets/logs


JAVA="java $XMX -Dlogdir=$LOG_DIR -Dlogfile=$HTML_LOG -Dlogat=$LOGAT -Dlog=$LOG -cp .:./target/twitter-0.0.1-SNAPSHOT.jar "
