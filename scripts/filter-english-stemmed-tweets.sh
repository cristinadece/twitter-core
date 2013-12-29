#!/usr/bin/env bash


EXPECTED_ARGS=2

E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` tweets.json[.gz] filtered-tweets.tsv.[.gz]"
  exit $E_BADARGS
fi

source scripts/config.sh

INPUT=$1
OUTPUT=$2

echo "filter english tweets in $INPUT in $OUTPUT"
$JAVA it.cnr.isti.hpc.twitter.cli.FilterEnglishStemmedTweetsCLI -input $INPUT -output $OUTPUT

echo "output in $OUTPUT"


