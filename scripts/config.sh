#!/usr/bin/env bash

VERSION="0.0.1-SNAPSHOT"
XMX="-Xmx2000m"
#LOG=INFO
PROJECT_NAME=twitter
LOG=OFF
LOGAT=1000
CLI=it.cnr.isti.hpc.twitter.cli
E_BADARGS=65

JAVA="java $XMX -Dlogat=$LOGAT -Dlog=$LOG -cp ./target/$PROJECT_NAME-$VERSION-jar-with-dependencies.jar "
