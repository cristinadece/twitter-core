#!/usr/bin/env bash



source scripts/config.sh
EXPECTED_ARGS=3
E_BADARGS=65

BUCKET_SIZE_IN_MINUTES=30

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` buckets-dir trend-file "
  exit $E_BADARGS
fi


echo "given the folder with the buckets $1, generates the trend in $2"

$JAVA it.cnr.isti.hpc.twitter.trends.WordFrequencyCLI -input $1 -output $2 


