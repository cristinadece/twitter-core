package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeLineFilteringCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeLineFilteringCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ UserDownloadCLI.class
			+ " -input originalTweetFile -output outputFileName";

	private static String[] params = new String[] { INPUT, OUTPUT };

	TimeLineFilteringCLI(String[] args) {
		super(args, params, USAGE);
	}

	private static final String URL_PATTERN = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	private static final String URL = "__URL__";

	public static void main(String[] args) {

		TimeLineFilteringCLI cli = new TimeLineFilteringCLI(args);

		cli.openInputAndOutput();

		String row = null;

		while ((row = cli.readLineFromInput()) != null) {

			JsonTweet tweet = null;

			try {
				tweet = JsonTweet.parseTweetFromJson(row);
			} catch (Exception e) {
				logger.warn("invalid tweet {} - {} ", e.toString(), row);
				continue;
			}

			// scarto i retweet
			if (!tweet.isRetweet()) {
				if (tweet.hasUrl()) {
					// modifico e sostituisco il testo del tweet
					String text = tweet.getText().replaceAll(URL_PATTERN, URL);
					tweet.setText(text);

					cli.writeLineInOutput(tweet.toJson()); //
				} else { // PERCHE NON LEVARE ELSE E METTERE UNA SOLO SCRITTURA?
					cli.writeLineInOutput(tweet.toJson()); //
				}
			}
		}
		cli.closeInputAndOuput();
	}
}
