#!/bin/env bash



source scripts/config.sh
EXPECTED_ARGS=3
E_BADARGS=65

BUCKET_SIZE_IN_MINUTES=30

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` input-dumps-dir output-bucket-dir "
  exit $E_BADARGS
fi


echo "generate italian buckets from $1 in $2, one bucket every $BUCKET_SIZE_IN_MINUTES minutes"

$JAVA it.cnr.isti.hpc.twitter.trends.SplitFileInTimeBucketsCLI -input $1 -output $2  -interval $BUCKET_SIZE_IN_MINUTES

