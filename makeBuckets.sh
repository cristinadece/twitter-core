#!/bin/bash

FILES=/Users/cris/Documents/workspace/analytics/data-warehouse/*
jar=./target/twitter-0.0.1-SNAPSHOT-jar-with-dependencies.jar

for f in $FILES
do
	echo "Processing $f ...."
	java -cp $jar it.cnr.isti.hpc.twitter.timebuckets.SplitFileInTimeBucketsCLI -input $f -output /Users/cris/Documents/workspace/analytics/buckets/ -interval 1800000
done 
