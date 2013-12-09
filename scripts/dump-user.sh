#!/usr/bin/env bash


EXPECTED_ARGS=1
E_BADARGS=65

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` user "
  exit $E_BADARGS
fi

source scripts/config.sh

USER=$1
OUTPUT=$USER".json.gz"
echo "dump tweets from user $USER"
#DA RIMPIAZZARE CON LA CLI CHE PRENDA USER E FILE DI OUTPUT 
$JAVA $CLI.UserDownloadCLI -input $USER -output $OUTPUT

echo "tweets from user $USER dumped in $OUTPUT"

