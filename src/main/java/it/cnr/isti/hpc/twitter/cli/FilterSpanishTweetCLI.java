package it.cnr.isti.hpc.twitter.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;



public class FilterSpanishTweetCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeLineFilteringCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ FilterSpanishTweetCLI.class
			+ " -input originalTweetFile -output fileOutPut";

	private static String[] params = new String[] { INPUT, OUTPUT }; 

	private static String vocabolaryPathIta = "src/main/resources/dic/italian";
	private static String vocabolaryPathEsp = "src/main/resources/dic/spanish";

	
	public FilterSpanishTweetCLI(String[] args) {
		super(args, params, USAGE);
	}


	public static void main(String[] args) {

		FilterSpanishTweetCLI cli = new FilterSpanishTweetCLI(args);

		Vocabolary vocabolaryIta = new Vocabolary(vocabolaryPathIta);

		if (vocabolaryIta == null || vocabolaryIta.size()==0) {
			logger.info("Errore durante l'inizializzazione del volcabolario Italiano");
			return;
		}
		
		Vocabolary vocabolaryEsp = new Vocabolary(vocabolaryPathEsp);
		
		if (vocabolaryEsp == null || vocabolaryEsp.size()==0) {
			logger.info("Errore durante l'inizializzazione del volcabolario Spagnolo");
			return;
		}
		
		cli.openInputAndOutput();

		ProgressLogger pl = new ProgressLogger("{} Scanned Tweet...", 50000);

		logger.info("Vocabolari inizializzati: ita {} - esp {}", vocabolaryIta.size(), vocabolaryEsp.size());

		String row = null;
		JsonTweet jst = null;
		
		while ((row = cli.readLineFromInput()) != null) {

			pl.up();

			try {
				jst = JsonTweet.parseTweetFromJson(row);
			} catch (Exception e) {
				logger.warn("{}: {}", e.getMessage(),row);
				continue;
			}

			// scarto i retweet
			if (jst.isRetweet())
				continue;

			int  ita = 0, esp = 0;
			List<String> terms = jst.getTerms();
			for (String term : terms) {

				if (vocabolaryIta.contains(term))
					ita++;
				if (vocabolaryEsp.contains(term))
					esp++;
			}

			if (ita < (esp + 3))
				continue;

			cli.writeLineInOutput(jst.toJson());
			pl.up();
		}
		
		cli.closeInputAndOuput();
		logger.info("Fine...");
	}
}

class Vocabolary{
	
	private HashSet<String> vocabolarySet = new HashSet<String>();
	
	public Vocabolary(String vocabolaryPath) {
		
		BufferedReader infile = IOUtils.getPlainOrCompressedUTF8Reader(vocabolaryPath);

		String word = null;
		
		try {
			while ((word = infile.readLine()) != null) {
				vocabolarySet.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				infile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public boolean contains(String word){
		
		return vocabolarySet.contains(word);
	}
	
	public int size(){
		
		return vocabolarySet.size();
	}
}
