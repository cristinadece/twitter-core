package it.cnr.isti.hpc.twitter.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;

/**
 * This script is run for all files in a directory - a for in shell We delete
 * buckets older than 7 days
 * 
 * Call: java -cp $jar class it.cnr.isti.hpc.twitter.cli.SplitFileInTimeBucketsCLI 
 * -input timeBucketFile -output wordcountFile
 */

public class WordFrequencyCLI extends AbstractCommandLineInterface{
	
	private static final Logger logger = LoggerFactory
			.getLogger(SplitFileInTimeBucketsCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ WordFrequencyCLI.class
			+ " -input timeBucketFile -output wordcountFile";

	public WordFrequencyCLI() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
