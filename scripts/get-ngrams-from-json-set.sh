#!/usr/bin/env bash


EXPECTED_ARGS=3

E_BADARGS=65


if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` tweets.json[.gz] output-file -n #ngrams"
  exit $E_BADARGS
fi

source scripts/config.sh

INPUT=$1
OUTPUT=$2
N=$3
TMP=$(dirname $OUTPUT)/tmp

$JAVA it.cnr.isti.hpc.twitter.cli.TweetsToNgramsCLI -input $INPUT -output $TMP -n $N

sort $TMP | uniq > $OUTPUT

rm $TMP

echo "output in $OUTPUT"

