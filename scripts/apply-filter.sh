#!/usr/bin/env bash


source scripts/config.sh


EXPECTED_ARGS=3
E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` input-dumps-dir output-filtered-dumps-dir filtername"
  exit $E_BADARGS
fi



echo "applying filter $3 to files in $1"
$JAVA it.cnr.isti.hpc.twitter.cli.FilterTweetsCLI -input $1 -output $2 -filter $3

