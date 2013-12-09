#!/usr/bin/env bash


EXPECTED_ARGS=1
E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` dump_folder "
  exit $E_BADARGS
fi

source scripts/config.sh

DUMP=$1

echo "Dumping the stream of the tweets in folder $DUMP (kill to finish)"
$JAVA it.cnr.isti.hpc.twitter.streaming.TwitterDownloadCLI -input $DUMP

echo "Stream dumped in $DUMP"

