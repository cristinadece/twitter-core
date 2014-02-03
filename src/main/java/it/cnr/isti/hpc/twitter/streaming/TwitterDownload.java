package it.cnr.isti.hpc.twitter.streaming;

import it.cnr.isti.hpc.property.ProjectProperties;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

	private static final Logger logger = LoggerFactory
			.getLogger(DateUtils.class);
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

		// System.out.println(cal.ZONE_OFFSET);

		// System.out.println(sdf.format(cal.getTime()));

		return sdf.format(cal.getTime());

	}

	public static Date getDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.warn("parsing the date {} ", date);
			return null;
		}
	}

}

public class TwitterDownload extends StatusAdapter {
	private TwitterStream twitter;
	private File dumpDir = null;
	private OutputStreamWriter writer;
	private String currentDate = null;
	private long fileLifespanInMillis;

	ProjectProperties properties = new ProjectProperties(TwitterDownload.class);
	private final String rootfile = properties.get("twitter_dump");

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

		dumpDir = new File(dirName);
		boolean success = dumpDir.mkdir();
		if (!success) {
			System.out.println("Couldn't create directory!");
			System.exit(-1);
		} else {
			System.out.println("Successfully created directory!");
		}

		return dumpDir;

	}

	public TwitterDownload(String dirName) {
		super();
		init(dirName);
		fileLifespanInMillis = (long) properties
				.getInt("file.lifespan.in.days") * 24 * 60 * 60 * 1000;

	}

	@Override
	public void onStallWarning(StallWarning stallWarning) {
		System.out.println(stallWarning);
	}

	private void init(String dirName) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true)
				.setDebugEnabled(true)
				.setOAuthConsumerKey(properties.get("oauth.consumerKey"))
				.setOAuthConsumerSecret(properties.get("oauth.consumerSecret"))
				.setOAuthAccessToken(properties.get("oauth.accessToken"))
				.setOAuthAccessTokenSecret(
						properties.get("oauth.accessTokenSecret"))
				.setGZIPEnabled(true);

		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
		twitter = tf.getInstance();
		logger.info("authorization enabled: {}", twitter.getAuthorization()
				.isEnabled());

		dumpDir = new File(dirName);

		if (!dumpDir.exists()) {
			dumpDir = makeDir(dirName);
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

				// remove file older than lifespan set in properties
				removeOldDumpFiles();

				String filename = dumpDir.getPath() + "/" + rootfile + d
						+ ".txt.gz";
				int i = 0;
				File ff = new File(filename);
				if (ff.exists()) {
					filename = dumpDir.getPath() + "/" + rootfile + d + "-" + i
							+ ".txt.gz";
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

			}

			private void removeOldDumpFiles() {
				for (File f : dumpDir.listFiles()) {
					if (isOld(f)) {
						logger.info("removing file {}, lifespan expired",
								f.getName());
						f.delete();
					}
				}
			}

			private boolean isOld(File f) {
				String name = f.getName();
				String dt = name.substring(rootfile.length(),
						rootfile.length() + 8);
				Date filedate = DateUtils.getDate(dt);
				Calendar cal = Calendar.getInstance();
				long delta = (cal.getTime().getTime() - filedate.getTime());
				return (delta > fileLifespanInMillis);
			}

			private void storeDeletionNoticeJSON(String rawJSON) {
				// store status deletion notice json
			}

			// @Override
			// public void onTrackLimitationNotice(int numberOfLimitedStatuses)
			// {
			// }
			//
			// @Override
			// public void onException(Exception ex) {
			// ex.printStackTrace();
			//
			// }
			//
			// @Override
			// public void onScrubGeo(long arg0, long arg1) {
			// // TODO Auto-generated method stub
			//
			// }

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

		};

		twitter.addListener(listener);

		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitter.sample();

	}
}
