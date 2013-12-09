package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.log.ProgressLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/* 
 * Scandisce il file (risultato di TweetsToNgramsCLI in cui si elencano gli ngrammi)
 * in input riga per riga contando le linee uguali, 
 * produce in output un file del formato "riga \t occorenza(riga)"
 */
public class NgramsLinearCountCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory.getLogger(NgramsLinearCountCLI.class);

	private static final String USAGE = "java -cp $jar " + NgramsLinearCountCLI.class + " -input ngrams_sorted_list -output output_file";

	private static String[] params = new String[] { INPUT, OUTPUT };
	
	private static String OUTPUTFORMAT = "%s\t%s";
	
	public NgramsLinearCountCLI(String[] args) {
		super(args, params, USAGE);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NgramsLinearCountCLI cli = new NgramsLinearCountCLI(args);
		
		logger.info("Input = {}",  cli.getInput());
		logger.info("Output = {}",  cli.getOutput() );
		
		cli.openInputAndOutput();
		
		long l = 1;		
		
		String current = cli.readLineFromInput();
		
		if(current == null){
			logger.info("Empty File.");
			cli.closeInputAndOuput();
			return;
		}
		
		ProgressLogger pl = new ProgressLogger("{} - scanned row", 100000);
		
		String next = null;
		
		while( (next = cli.readLineFromInput()) != null){
			
			pl.up();
			
			if(current.compareTo(next)==0){
				l++;
			}
			else{
				cli.writeLineInOutput(String.format(OUTPUTFORMAT, current, l));
				l=1;
				current = next;
			}
		}
		
		cli.writeLineInOutput(String.format(OUTPUTFORMAT, current, l));
		
		cli.closeInputAndOuput();
		logger.info("= Fine =");
	}

}
