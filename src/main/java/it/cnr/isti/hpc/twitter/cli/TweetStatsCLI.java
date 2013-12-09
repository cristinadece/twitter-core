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
// cambierei il nome in qualcosa di piu' significativo, 
// o almeno aggiungerei un commento qua.. ho dovuto leggere la classe per capire cosa faceva :) 
public class TweetStatsCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeLineFilteringCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ TweetStatsCLI.class
			+ " -input originalTweetFile -v1 vocabolaryFileIta -v2 vocabolaryFileEsp -output fileOutPut";

	private static String[] params = new String[] { INPUT, OUTPUT }; // "v1",
																		// "v2",
																		// OUTPUT
																		// };

	private static String vocabolaryPathIta = "dic/italian";
	private static String vocabolaryPathEsp = "dic/spanish";

	public TweetStatsCLI(String[] args) {
		super(args, params, USAGE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TweetStatsCLI cli = new TweetStatsCLI(args);

		HashSet<String> vocabolaryIta = new HashSet<String>();
		HashSet<String> vocabolaryEsp = new HashSet<String>();

		cli.openInputAndOutput();

		/*
		 * String vocabolaryPathIta = tsc.getParam("v1"); String
		 * vocabolaryPathEsp = tsc.getParam("v2");
		 */

		ProgressLogger pl = new ProgressLogger("{} Scanned Tweet...", 50000);

		// fai un oggetto vocabolario, con un metodo contains
		// i file del vocabolario li devi mettere in main/resources e leggere
		// dal jar.
		initVocabolary(vocabolaryPathIta, vocabolaryIta);

		if (vocabolaryIta.size() == 0) {
			logger.info("Errore durante l'inizializzazione del volcabolario Italiano");
			return;
		}

		initVocabolary(vocabolaryPathEsp, vocabolaryEsp);

		if (vocabolaryEsp.size() == 0) {
			logger.info("Errore durante l'inizializzazione del volcabolario Spagnolo");
			return;
		}
		logger.info("Vocabolari inizializzati: ita {} - esp {}",
				vocabolaryIta.size(), vocabolaryEsp.size());

		// try {

		// non hai bisogno
		// infile = IOUtils.getPlainOrCompressedUTF8Reader(input);
		// outfile = IOUtils.getPlainOrCompressedUTF8Writer(output);

		String row = null;
		JsonTweet jst = null;
		// while ((row = infile.readLine()) != null) {
		while ((row = cli.readLineFromInput()) != null) {

			pl.up();

			try {
				jst = JsonTweet.parseTweetFromJson(row);
			} catch (Exception e) {
				logger.info("Errore durante la lettura: {}", e.getMessage());
				continue;
			}

			// scarto i retweet
			if (jst.isRetweet())
				continue;

			int length = 0, count = 0, ita = 0, esp = 0;
			// float itaRatio = 0, espRatio=0;
			String tweet = jst.getText();

			length = tweet.length();

			// Iterator<String> iter = jst.getTerms().iterator();

			List<String> terms = jst.getTerms();

			for (String term : terms) {

				// while (iter.hasNext()) {

				count++;
				// String next = (String) iter.next();

				if (vocabolaryIta.contains(term))
					ita++;
				if (vocabolaryEsp.contains(term))
					esp++;

			}

			if (ita < (esp + 3))
				continue;

			/*
			 * if (count != 0){ itaRatio = (float) ita / count; espRatio =
			 * (float) esp / count; }
			 */

			/*
			 * StringBuffer sb = new StringBuffer();
			 * sb.append(tweet).append("\t").append(length).append("\t")
			 * .append(count).append("\t").append(ita).append("\t")
			 * .append(itaRatio ).append("\t").append(esp).append("\t").append
			 * (espRatio).append("\n"); outfile.write(sb.toString());
			 */

			// outfile.write(jst.toJson());
			// outfile.write("\n");
			cli.writeLineInOutput(jst.toJson());

			pl.up();

		}
		cli.closeInputAndOuput();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// infile.close();
		// outfile.close();
		// } catch (Exception e) {
		// }
		// }
		// logger.info("Fine...");
	}

	private static void initVocabolary(String vocabolaryPath, HashSet<String> hs) {

		BufferedReader infile = IOUtils
				.getPlainOrCompressedUTF8Reader(vocabolaryPath);

		String word = null;
		try {
			while ((word = infile.readLine()) != null) {
				hs.add(word);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				infile.close();
			} catch (IOException e) {
			}
		}

	}

}
