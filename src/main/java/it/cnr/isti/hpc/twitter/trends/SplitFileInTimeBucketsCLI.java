package it.cnr.isti.hpc.twitter.trends;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;
import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.twitter.cli.TweetStatsCLI;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

/**
 * This script is run for all files in a directory - a for in shell We delete
 * buckets older than 7 days
 * 
 * Call: java -cp $jar class it.cnr.isti.hpc.twitter.cli.SplitFileInTimeBucketsCLI -input
 * originalTweetFile -output output-folder -interval timeInMilisInterval
 */

public class SplitFileInTimeBucketsCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(SplitFileInTimeBucketsCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ SplitFileInTimeBucketsCLI.class
			+ " -input originalTweetFile -output output-folder -interval timeInMilisInterval";

	private static String[] params = new String[] { INPUT, OUTPUT, "interval" };

	private static long interval = 1800000; // half an hour

	final static int LOOP_DELAY = 900000; // 15 min

	public SplitFileInTimeBucketsCLI(String[] args) {
		super(args, params, USAGE);
	}

	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	private static boolean isOld(File f) {
		String name = f.getName();
		if (!name.endsWith("json.gz"))
			return false;
		long timeInMilis = Long.parseLong(name.replace(".json.gz", ""));
		long now = System.currentTimeMillis();
		return ((now - timeInMilis) > 604800000); // 604800000 = 7 days
	}

	public static void deleteOlderFiles(SplitFileInTimeBucketsCLI cli) {
		File dumpDir = new File(cli.getOutput());
		for (File f : dumpDir.listFiles()) {
			System.out.println(f.getName());
			if (isOld(f)) {
				logger.info("removing file {}, lifespan expired", f.getName());
				f.delete();
			}
		}
	}

	public static void dumpTweetBucket(SplitFileInTimeBucketsCLI cli)
			throws InvalidTweetException, IOException {

		cli.openInput();

		String firstTweetString = cli.readLineFromInput();
		long timePointer = JsonTweet.parseTweetFromJson(firstTweetString)
				.getDateInMilliseconds();

		// initialize current file name with time pointer
		String outputFileString = cli.getOutput() + timePointer + ".json.gz";
		BufferedWriter out = IOUtils
				.getPlainOrCompressedUTF8Writer(outputFileString);

		long currentTime;

		boolean done = false;
		while (!done) {
			String line = cli.readLineFromInput();

			// check if line is valid json, otherwise we may have half of tweet
			if ((line == null) || (!isJSONValid(line))) {
				try {
					logger.info("Line is NULL");
					Thread.sleep(LOOP_DELAY);
					line = cli.readLineFromInput();
					if (line == null) {
						out.close();
						Thread.interrupted();
						break;
					}

				} catch (InterruptedException e) {
					logger.error("Error splitting tweet file {} ({})", INPUT,
							e.toString());
				}
			}

			JsonTweet tweet = JsonTweet.parseTweetFromJson(line);
			currentTime = tweet.getDateInMilliseconds();

			if ((currentTime >= timePointer)) {
				if (currentTime < (timePointer + interval)) {
					if (tweet.isItalian()) {
						out.write(line);
						out.write("\n");
					}
				} else {
					// change pointer to the next interval
					out.close();
					timePointer = currentTime;
					// open a new file for writing
					outputFileString = cli.getOutput() + timePointer
							+ ".json.gz";
					out = IOUtils
							.getPlainOrCompressedUTF8Writer(outputFileString);
					if ((currentTime >= timePointer)
							&& (currentTime < (timePointer + interval))) {
						if (JsonTweet.parseTweetFromJson(line).isItalian()) {
							out.write(line);
							out.write("\n");
						}
					}
				}

			} else {
				logger.info("This tweet isn't orderred chronologically {}", line);
			}

		}
	}

	public static void main(String[] args) throws InvalidTweetException,
			IOException {
		SplitFileInTimeBucketsCLI cli = new SplitFileInTimeBucketsCLI(args);
		interval = Long.parseLong(cli.getParam("interval"));
		
		// check if output folder exists
		File outputFolder = new File(cli.getOutput());
		if (!outputFolder.exists()) {
			logger.error("cannot input dir {}", outputFolder.getAbsolutePath());
			System.exit(-1);
		}
		
		dumpTweetBucket(cli);
		deleteOlderFiles(cli);
	}
}
