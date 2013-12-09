package it.cnr.isti.hpc.twitter.cli;

import net.sf.junidecode.Junidecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.log.ProgressLogger;

import static net.sf.junidecode.Junidecode.*;

public class Utf8ToAsciiTweetCLI extends AbstractCommandLineInterface{
	
	
	private static final Logger logger = LoggerFactory.getLogger( Utf8ToAsciiTweetCLI.class);

	private static final String USAGE = "java -cp $jar " + Utf8ToAsciiTweetCLI.class + " -input utf8File -output destFile";

	private static String[] params = new String[] { INPUT, OUTPUT }; 

	
	public Utf8ToAsciiTweetCLI(String[] args) {
		super(args, params, USAGE);
	}
	
	public static void main(String[] args) {
		
		Utf8ToAsciiTweetCLI cli = new Utf8ToAsciiTweetCLI(args);
		
		cli.openInputAndOutput();
		
		ProgressLogger pl = new ProgressLogger("{} Converted Tweet...", 1000);

		String row=null;
		while((row=cli.readLineFromInput())!=null){
			cli.writeLineInOutput(unidecode(row));
			pl.up();
		}
		cli.closeInputAndOuput();
		logger.info("Fine...");

	}

}
