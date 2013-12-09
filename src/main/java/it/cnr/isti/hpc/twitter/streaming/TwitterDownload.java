package it.cnr.isti.hpc.twitter.streaming;

import it.cnr.isti.hpc.property.ProjectProperties;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

class DateUtils {

	/*
	 * System.out.println(DateUtils.now("dd MMMMM yyyy"));
	 * System.out.println(DateUtils.now("yyyyMMdd hh:mm"));
	 * System.out.println(DateUtils.now("dd.MM.yy"));
	 * System.out.println(DateUtils.now("MM/dd/yy"));
	 */
	public static final String DATE_FORMAT_NOW = "yyyyMMdd";

	// public Writer writer ;
	// public static String currentDate = "";

	public static String now() {
		
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
		
		//System.out.println(cal.ZONE_OFFSET);
		
		//System.out.println(sdf.format(cal.getTime()));
		
		return sdf.format(cal.getTime());
		

	}

}

public class TwitterDownload extends StatusAdapter {
	private TwitterStream twitter;
	private File f = null;
	private OutputStreamWriter writer;
	private String currentDate = null;
	
	
	ProjectProperties properties = new ProjectProperties(TwitterDownload.class);
	private String rootfile = properties.get("twitter_dump");


	private static final Logger logger = LoggerFactory
			.getLogger(TwitterDownload.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws TwitterException {
		TwitterDownload d = new TwitterDownload(args.toString());
		d.dumpSampleStream();

	}

	public File makeDir(String dirName) {
		 
		// String rootfile = properties.getProperty("twitter_dump");
		// String dir = properties.getProperty("twitter_dump_directory");

		// put path

		f = new File(dirName);
		boolean success = f.mkdir();
		if (!success) {
			System.out.println("Couldn't create directory!");
			System.exit(-1);
		} else {
			System.out.println("Successfully created directory!");
		}

		return f;

	}

	public TwitterDownload(String dirName) {
		super();
		init(dirName);
		
	}
	
	@Override
	public void onStallWarning(StallWarning stallWarning) {
	    System.out.println(stallWarning);
	}
	
	private void init(String dirName){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true)
				.setDebugEnabled(true)
				.setOAuthConsumerKey(
						properties.get("oauth.consumerKey"))
				.setOAuthConsumerSecret(
						properties.get("oauth.consumerSecret"))
				.setOAuthAccessToken(
						properties.get("oauth.accessToken"))
				.setOAuthAccessTokenSecret(
						properties.get("oauth.accessTokenSecret"))
				.setGZIPEnabled(true);
		
		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
		twitter = tf.getInstance();
		logger.info("authorization enabled: {}",twitter.getAuthorization().isEnabled());

		f = new File(dirName);

		if (!f.exists()) {
			f = makeDir(dirName);
		}
	}
	
	
	

	public TwitterDownload() {
		init(properties.get("twitter_dump_directory"));
	}
	
	public void dumpSampleStream() {

		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {

				String rawJSON = DataObjectFactory.getRawJSON(status);

				storeStatusJSON(rawJSON);

			}

			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				String rawJSON = DataObjectFactory
						.getRawJSON(statusDeletionNotice);
				storeDeletionNoticeJSON(rawJSON);
			}

			private void storeStatusJSON(String rawJSON) {
				// store status json
				
				String d = DateUtils.now();

				if (d.equals(currentDate)) {
					try {
						writer.write(rawJSON);

						writer.write("\n");
					} catch (IOException e) {
						logger.error("writing the output file ({})",
								e.toString());
						System.exit(-1);
					}
					return;
				}
				// if i'm here then d != currentDate

				// if i wrote something, i close the writer.
				if (writer != null)
					try {
						writer.close();
					} catch (IOException e) {
						logger.error("closing the current json dump ({})",
								e.toString());
						System.exit(-1);
					}

				currentDate = d;

				String filename = f.getPath() + "/" + rootfile + d + ".txt.gz";
				int i = 0; 
				File ff = new File(filename);
				if (ff.exists()){
					filename = f.getPath() + "/" + rootfile + d + "-"+i+".txt.gz";
					ff = new File(filename);
					i++;
				}
				logger.info("opening file {} to store tweets of the day",
						filename);

				FileOutputStream dest = null;

				try {
					dest = new FileOutputStream(ff.getAbsolutePath());
				} catch (FileNotFoundException e) {
					logger.error("cannot find file {} ({})", filename,
							e.toString());
					System.exit(-1);
				}
				try {
					GZIPOutputStream compressedFile = new GZIPOutputStream(
							new BufferedOutputStream(dest));

					writer = new OutputStreamWriter(compressedFile, "UTF8");

					writer.write(rawJSON);
					writer.write("\n");
				} catch (Exception e) {
					logger.error("writing the file {} ({})", filename,
							e.toString());
					System.exit(-1);
				}

				// // current date NOW
				// String d = DateUtils.now();
				//
				// // list of files in directory
				//
				// String[] files = f.();
				//
				// // if list is empty
				// if (files.length == 0) {
				// StringBuilder filename = new StringBuilder();
				// filename.append(rootfile).append(d).append(".txt.gz");
				// DateUtils.currentDate = d;
				//
				// FileOutputStream dest = new FileOutputStream(
				// filename.toString());
				// GZIPOutputStream compressedFile = new GZIPOutputStream(
				// new BufferedOutputStream(dest));
				// writer = new OutputStreamWriter(compressedFile, "UTF-8");
				// writer.write(rawJSON);
				// }
				// // if list is not empty
				// else {
				// // list NOT empty, but the date is the SAME as the current
				// // date file
				// if (d == DateUtils.currentDate) {
				// writer.write(rawJSON);
				// }
				//
				// // list is NOT empty, but a NEW current date
				// else {
				// // close current file
				// writer.close();
				// DateUtils.currentDate = d;
				//
				// // create a new one with the new date
				//
				// StringBuilder filename = new StringBuilder();
				// filename.append(rootfile).append(d).append(".txt.gz");
				//
				// FileOutputStream dest = new FileOutputStream(
				// filename.toString());
				// GZIPOutputStream compressedFile = new GZIPOutputStream(
				// new BufferedOutputStream(dest));
				// writer = new OutputStreamWriter(compressedFile, "UTF-8");
				// writer.write(rawJSON);
				// }
				//
				// }

			}

			private void storeDeletionNoticeJSON(String rawJSON) {
				// store status deletion notice json
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
				
			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

		};

		twitter.addListener(listener);

		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitter.sample();

	}

}
