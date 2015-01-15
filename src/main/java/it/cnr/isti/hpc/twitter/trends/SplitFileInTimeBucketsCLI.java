package it.cnr.isti.hpc.twitter.trends;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.streaming.TwitterDownload.DateUtils;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * This script is run for all files in a directory - a for in shell We delete
 * buckets older than 7 days
 * 
 * This is executed in makeBuckets.sh file
 * 
 * Call: java -cp $jar class
 * it.cnr.isti.hpc.twitter.cli.SplitFileInTimeBucketsCLI -input
 * originalTweetFile -output output-folder -interval timeInMilisInterval
 */

public class SplitFileInTimeBucketsCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(SplitFileInTimeBucketsCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ SplitFileInTimeBucketsCLI.class
			+ " -input full-dump-folder -output output-folder -interval minutes";

	private static String[] params = new String[] { INPUT, OUTPUT, "interval" };

	public static long interval = 1800000; // half an hour

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
		return ((now - timeInMilis) > interval * 7); // 604800000 = 7 days
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

		String dirname = cli.getInput();

		BufferedReader br = null;
		long bucketStartTime = System.currentTimeMillis();

		File outputFile = new File(cli.getOutput(), bucketStartTime
				+ ".json.gz");
		BufferedWriter out = IOUtils.getPlainOrCompressedUTF8Writer(outputFile
				.getAbsolutePath());
		int count = 0;
		boolean far = true;
		while (true) {

			File f = new File(dirname, "gardenhose-full-dump-"
					+ DateUtils.now() + ".json.gz");
			if (!f.exists()) {
				logger.error("cannot find {}", f.getAbsolutePath());

				try {
					Thread.sleep(LOOP_DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			br = IOUtils.getPlainOrCompressedReader(f.getAbsolutePath());
			logger.info("reading {}", f.getAbsolutePath());
			// initialize current file name with time pointer

			boolean done = false;

			while (!done) {
				String line = br.readLine();
				count++;
				long endBucket = bucketStartTime + interval;

				// check if line is valid json, otherwise we may have half of
				// tweet
				if ((line == null) || (!isJSONValid(line))) {
					try {
						logger.error("Line is null, sleeping");
						Thread.sleep(LOOP_DELAY);
						line = br.readLine();
						if (line == null) {
							done = false;
							break;
						}

					} catch (InterruptedException e) {
						logger.error("Error splitting tweet file {} ({})",
								INPUT, e.toString());
					}

				}
				JsonTweet tweet = JsonTweet.parseTweetFromJson(line);
				long currentTweetTime = tweet.getDateInMilliseconds();
				long delta = System.currentTimeMillis() - currentTweetTime;
				if (delta > 0 && delta < 60000 * 3) {
					logger.info("troppo vicino alla fine.. sto bono vai");
					try {
						Thread.sleep(60000); // wait a minute
					} catch (InterruptedException e) {

					}

				}

				if ((currentTweetTime >= endBucket)) {
					logger.info("close bucket {}.json", bucketStartTime);
					out.close();
					bucketStartTime = System.currentTimeMillis();

					outputFile = new File(cli.getOutput(), bucketStartTime
							+ ".json.gz");
					logger.info("open bucket {}.json", bucketStartTime);
					out = IOUtils.getPlainOrCompressedUTF8Writer(outputFile
							.getAbsolutePath());
					endBucket = bucketStartTime + interval;
					logger.info("delefe old {}.json", bucketStartTime);
					deleteOlderFiles(cli);
				}

				if ((currentTweetTime > bucketStartTime)
						&& (currentTweetTime < endBucket)) {
					far = false;
					if (tweet.isItalian()) {
						out.write(line);
						out.write("\n");
					}
				} else {
					if (currentTweetTime < bucketStartTime) {
						Date d = new Date(currentTweetTime);
						Date d1 = new Date(bucketStartTime);
						logger.info("skipping tweet, before time {} < {}", d,
								d1);

						logger.info("skipping 999999 records...");
						if (far) {
							for (int i = 0; i < 99999; i++) {
								br.readLine();
								count++;
							}
						}
						logger.info("...done");
					}
				}

			}
		}
	}

	public static void main(String[] args) throws InvalidTweetException,
			IOException {
		SplitFileInTimeBucketsCLI cli = new SplitFileInTimeBucketsCLI(args);
		interval = Long.parseLong(cli.getParam("interval"));
		interval = interval * 60 * 1000; // from minutes to milliseconds
		// check if output folder exists
		File outputFolder = new File(cli.getOutput());
		if (!outputFolder.exists()) {
			logger.error("cannot input dir {}", outputFolder.getAbsolutePath());
			System.exit(-1);
		}
		dumpTweetBucket(cli);

	}
}
