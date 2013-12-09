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
TMP_DIR=$(dirname $OUTPUT)/tmp

echo "count tweet from json in  $INPUT"

TMP=$TMP_DIR/list
TMP_SORTED=$TMP_DIR/sorted

mkdir -p $TMP_DIR

$JAVA it.cnr.isti.hpc.twitter.cli.TweetsToNgramsCLI -input $INPUT -output $TMP -n $3

sort $TMP > $TMP_SORTED
rm $TMP

$JAVA it.cnr.isti.hpc.twitter.cli.NgramsLinearCountCLI -input $TMP_SORTED -output $OUTPUT

rm $TMP_SORTED
rm -r $TMP_DIR

echo "output in $OUTPUT"
