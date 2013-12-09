package it.cnr.isti.hpc.twitter.cli;

import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;


/* Preso un set di tweet in formato json, suddivide tutte le frasi in ngrammi verificando che siano formati 
 * da sole lettere dell'alfabeto itailano e li scrive (in LowerCase) in un file di output riga per riga.
 */

public class TweetsToNgramsCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory.getLogger(TweetsToNgramsCLI.class);

	private static final String USAGE = "java -cp $jar " + TweetsToNgramsCLI.class + " -input jsonTweetsFile -output fileName -n number of grams";

	private static final String[] params = new String[] { INPUT, OUTPUT,"n" };
	
	
	public TweetsToNgramsCLI(String[] args) {
		super(args, params, USAGE);
	}

	
	public static void main(String[] args) {

		TweetsToNgramsCLI cli = new TweetsToNgramsCLI(args);

		int n = Integer.valueOf(cli.getParam("n"));
		
		logger.info("Input = {}",  cli.getInput());
		logger.info("Output = {}",  cli.getOutput() );
		
		if (n < 1) {
			logger.warn("Invalid parameter n: {} (must be 0<n<5)", n);
			System.exit(-1);
		}
		
		logger.info("N = {}", n);
		
		cli.openInputAndOutput();
		ProgressLogger pl = new ProgressLogger("{} - Scanned weet...", 100000);
		
		JsonTweet jst = null;
		String row = null;
		List<String> terms=  null;
		
		while ((row = cli.readLineFromInput()) != null) {
			try {
				jst = JsonTweet.parseTweetFromJson(row);
			} catch (InvalidTweetException e) {
				logger.warn("{} - {}", e.getMessage(), row);
				continue;
			}

			terms=jst.getTerms();
			
			if(n==1){
				terms.add(0,"_");
			}
			else{
				for(int i = 0 ; i<n-1; i++)
					terms.add(0,"_");
			}
			
			String ngram = null;
			
			for (int i = 0; (i + n - 1) < terms.size(); i++){
				
				ngram=listToLowerCaseSting(terms.subList(i, (i + n)));
				
				if(Pattern.matches("[a-z' _]{"+ngram.length()+"}", ngram))
					cli.writeLineInOutput(ngram.replace(" ", "-"));
			}
			
			pl.up();
		}
		
		cli.closeInputAndOuput();
		logger.info("= Fine =");
	}
	
	private static String listToLowerCaseSting(List<String> l){
		
		StringBuffer sb = new StringBuffer();
		for (int i =0 ; i<l.size() ; i++){
			sb.append(l.get(i).toLowerCase());
			if(i!=l.size()-1)
				sb.append(" ");
		}
		//logger.info("listToLowerCase() = {}",  sb.toString() );
		return sb.toString();
	}
	
}
