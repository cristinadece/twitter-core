#!/usr/bin/env bash


source scripts/config.sh


EXPECTED_ARGS=0
E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0`"
  exit $E_BADARGS
fi



echo "Dumping the stream of the tweets (kill to finish)"
$JAVA it.cnr.isti.hpc.twitter.streaming.TwitterDownloadCLI

echo "Stream dumped"

