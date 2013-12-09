#!/usr/bin/env bash


EXPECTED_ARGS=2

E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` italian-tweets.json[.gz] filtered-tweets.json[.gz]"
  exit $E_BADARGS
fi

source scripts/config.sh

INPUT=$1
OUTPUT=$2

TMP=/tmp/twitter-tmp

echo "remove spanish tweets in $INPUT in $TMP"
$JAVA it.cnr.isti.hpc.twitter.cli.TweetStatsCLI -input $INPUT -output $TMP
echo "remove urls tweets in $TMP in $OUTPUT"
$JAVA it.cnr.isti.hpc.twitter.cli.TimeLineFilteringCLI -input $TMP -output $OUTPUT

echo "output in $OUTPUT"


