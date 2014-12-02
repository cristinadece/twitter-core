#!/bin/bash

FILES=/Users/cris/Documents/workspace/analytics/data-warehouse/*
jar=./target/twitter-0.0.1-SNAPSHOT-jar-with-dependencies.jar

for f in $FILES
do
	# we check if the file was modified less than 7 day ago, this way we don't calculate trends on very old files
	if test `find $f -mtime -7`
	then
		echo "Processing $f"
		java -cp $jar it.cnr.isti.hpc.twitter.trends.SplitFileInTimeBucketsCLI -input $f -output /Users/cris/Documents/workspace/analytics/buckets/ -interval 1800000
	fi
done
