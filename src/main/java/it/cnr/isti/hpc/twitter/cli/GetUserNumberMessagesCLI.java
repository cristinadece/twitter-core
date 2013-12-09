package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.util.Hashtable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetUserNumberMessagesCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(GetUserNumberMessagesCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ GetUserNumberMessagesCLI.class + " -input jsonTweetsFile -output fileName";

	private static String[] params = new String[] { INPUT, OUTPUT };

	private static Hashtable<String, Long> table = new Hashtable<String, Long>();

	public GetUserNumberMessagesCLI(String[] args) {
		super(args, params, USAGE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GetUserNumberMessagesCLI cli = new GetUserNumberMessagesCLI(args);

		cli.openInputAndOutput();

		ProgressLogger pl = new ProgressLogger("{} - Scanned Weets...", 10000);

		JsonTweet jst = null;
		String row = null;
		long init = 1;

		while ((row = cli.readLineFromInput()) != null) {
			try {
				jst = JsonTweet.parseTweetFromJson(row);
			} catch (InvalidTweetException e) {
				logger.warn("{} - {}", e.getMessage(), row);
				continue;
			}

			String user = jst.getUsername();
			Long n = table.get(user);

			if (n == null)
				table.put(user, init);
			else
				table.put(user, ++n);

			pl.up();
		}

		ProgressLogger plw = new ProgressLogger("{} - Writen Users...", 10000);

		Set<String> set = table.keySet();

		for (String u : set) {
			cli.writeLineInOutput(u + "\t" + table.get(u));
			plw.up();
		}
		
		cli.closeInputAndOuput();

	}

}
