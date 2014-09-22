package it.cnr.isti.hpc.twitter.trends;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
import it.cnr.isti.hpc.twitter.util.Text;

/**
 * This script is run for all files in a directory - a for in shell We delete
 * buckets older than 7 days
 * 
 * Call: java -cp $jar class it.cnr.isti.hpc.twitter.cli.SplitFileInTimeBucketsCLI 
 * -input timeBucketFile -output wordcountFile
 */

public class WordFrequencyCLI extends AbstractCommandLineInterface{
	
	private static final Logger logger = LoggerFactory
			.getLogger(SplitFileInTimeBucketsCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ WordFrequencyCLI.class
			+ " -input timeBucketFile -output wordcountFile -ngram numberofwordsinaphrase";
	
	private final List<String> stopItalian = Arrays.asList(new String[] {
			"adesso", "ai", "al", "alla", "allo", "allora", "altre", "altri",
			"altro", "anche", "ancora", "avere", "aveva", "avevano", "ben",
			"buono", "che", "chi", "cinque", "comprare", "con", "consecutivi",
			"consecutivo", "cosa", "cui", "da", "del", "della", "dello",
			"dentro", "deve", "devo", "di", "doppio", "due", "e", "ecco",
			"fare", "fine", "fino", "fra", "gente", "giu", "ha", "hai",
			"hanno", "ho", "il", "indietro", "invece", "io", "la", "lavoro",
			"le", "lei", "lo", "loro", "lui", "lungo", "ma", "me", "meglio",
			"molta", "molti", "molto", "nei", "nella", "no", "noi", "nome",
			"nostro", "nove", "nuovi", "nuovo", "o", "oltre", "ora", "otto",
			"peggio", "pero", "persone", "piu", "poco", "primo", "promesso",
			"qua", "quarto", "quasi", "quattro", "quello", "questo", "qui",
			"quindi", "quinto", "rispetto", "sara", "secondo", "sei", "sembra",
			"sembrava", "senza", "sette", "sia", "siamo", "siete", "solo",
			"sono", "sopra", "soprattutto", "sotto", "stati", "stato",
			"stesso", "su", "subito", "sul", "sulla", "tanto", "te", "tempo",
			"terzo", "tra", "tre", "triplo", "ultimo", "un", "una", "uno",
			"va", "vai", "voi", "volte", "vostro" });
	
	private HashMap<String, Keyword> wordMap;

	public WordFrequencyCLI() {
		wordMap = new HashMap<String, Keyword>();
	}
	
	public List<String> cleanTweetText(String tweetText){
		// the replace all is to remove puntuation
		List<String> words = new Text(tweetText.replaceAll("\\p{P}", "").toLowerCase()).getTerms();
		for (String s : words){
			if (stopItalian.contains(s)){
				words.remove(s);
			}	
		}
		return words;
	}
	
	public String getTimeinMilisFromFilename(WordFrequencyCLI cli){
		String filename = cli.getOutput();
		String timeinmilis = filename.split("/")[filename.length()-1].replace(".json.gz", "");
		return timeinmilis;
	}
	
	public void countOccurences(WordFrequencyCLI cli){
		cli.openInput();
		String row;
		JsonTweet tweet;
		Keyword kw;
		
		String timestamp = getTimeinMilisFromFilename(cli);
		
		while((row=cli.readLineFromInput())!=null){
			
			try {
				tweet = JsonTweet.parseTweetFromJson(row);
				List<String> tweetWords = cleanTweetText(tweet.getText());
				for (String word: tweetWords){
					if (wordMap.containsKey(word)){
						kw = wordMap.get(word);
						kw.addBucketFrequency(timestamp, 1);
						kw.addUserId(tweet.getUsers().getId());
						kw.addTweetId(tweet.getId());
					}
					else{
						kw = new Keyword(word, timestamp, tweet.getUsers().getId(), tweet.getId());
					}
				}
			} catch (InvalidTweetException e) {
				logger.warn("{} - {}", e.getMessage(), row);
				continue;
			}
		}
		
	}
	
	public void writeDictionaryFreq(WordFrequencyCLI cli){
		cli.openOutput();
		for (Keyword kw : wordMap.values()){
			StringBuilder sb = new StringBuilder();
			//sb.append();
			//cli.writeLineInOutput();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
