package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetTweetsFromJsonCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(GetTweetsFromJsonCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ GetUserNumberMessagesCLI.class
			+ " -input jsonTweetsFile -output fileName ";

	private static String[] params = new String[] { INPUT, OUTPUT };
	

	
	public GetTweetsFromJsonCLI(String[] args) {
		super(args, params, USAGE);
	}
	
	public static void main(String[] args) {
		
		GetTweetsFromJsonCLI cli = new GetTweetsFromJsonCLI(args);
		
		cli.openInputAndOutput();
		
		ProgressLogger pl = new ProgressLogger("{} Tweet...", 1000);

		String row=null;
		JsonTweet jst = null;
		
		while((row=cli.readLineFromInput())!=null){
			
			try {
				jst = JsonTweet.parseTweetFromJson(row);
			} catch (InvalidTweetException e) {
				logger.warn("{} - {}", e.getMessage(), row);
				continue;
			}
			
			cli.writeLineInOutput(jst.getText());
			pl.up();
		}
		
		cli.closeInputAndOuput();

	}

}
