#!/usr/bin/env bash


EXPECTED_ARGS=3

E_BADARGS=65


if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` tweets.json[.gz] output-file #grams"
  exit $E_BADARGS
fi

source scripts/config.sh

INPUT=$1
OUTPUT=$2
N=$3
TMP=$(dirname $OUTPUT)/tmp
TMP_SORTED=$(dirname $OUTPUT)/tmp.sort

$JAVA it.cnr.isti.hpc.twitter.cli.TweetsToNgramsCLI -input $INPUT -output $TMP -n $N

sort $TMP > $TMP_SORTED

rm $TMP

$JAVA it.cnr.isti.hpc.twitter.cli.NgramsLinearCountCLI -input $TMP_SORTED -output $OUTPUT

rm $TMP_SORTED

echo "output in $OUTPUT"
