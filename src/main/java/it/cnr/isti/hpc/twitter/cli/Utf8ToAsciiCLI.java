package it.cnr.isti.hpc.twitter.cli;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.log.ProgressLogger;

import static net.sf.junidecode.Junidecode.*;

public class Utf8ToAsciiCLI extends AbstractCommandLineInterface{
	
	
	private static final Logger logger = LoggerFactory.getLogger( Utf8ToAsciiCLI.class);

	private static final String USAGE = "java -cp $jar " + Utf8ToAsciiCLI.class + " -input utf8File -output destFile";

	private static String[] params = new String[] { INPUT, OUTPUT }; 

	
	public Utf8ToAsciiCLI(String[] args) {
		super(args, params, USAGE);
	}
	
	public static void main(String[] args) {
		
		Utf8ToAsciiCLI cli = new Utf8ToAsciiCLI(args);
		
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
