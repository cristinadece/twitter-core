#!/usr/bin/env bash

VERSION="0.0.1-SNAPSHOT"
XMX="-Xmx2000m"
#LOG=INFO
PROJECT_NAME=twitter
LOG=INFO
LOGAT=1000
CLI=it.cnr.isti.hpc.twitter.cli
E_BADARGS=65
HTML_LOG=/data/twitter/logs/


JAVA="java $XMX -Dlogfile=$HTML_LOG -Dlogat=$LOGAT -Dlog=$LOG -cp .:./target/$PROJECT_NAME-$VERSION-jar-with-dependencies.jar "
