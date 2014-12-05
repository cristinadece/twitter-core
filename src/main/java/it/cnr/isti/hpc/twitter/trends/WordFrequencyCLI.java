package it.cnr.isti.hpc.twitter.trends;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
import it.cnr.isti.hpc.twitter.util.Twokenize;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import com.google.gson.Gson;

/**
 * This script is run for all files in a directory - a for in shell 
 * We delete buckets older than 7 days
 * 
 * To create time buckets we must first run the script: makeBuckets.sh
 * This script splits each new tweet file downloaded from the Twitter 
 * Streaming API into smaller time buckets and puts everything in one
 * folder: buckets/
 * 
 * Call: java -cp $jar class
 * it.cnr.isti.hpc.twitter.cli.SplitFileInTimeBucketsCLI -input timeBucketFolder
 * -output wordcountFile
 */

public class WordFrequencyCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(WordFrequencyCLI.class);

	// maybe eliminate ngrams from param and do all for 1,2,3
	private static final String USAGE = "java -cp $jar "
			+ WordFrequencyCLI.class
			+ " -input timeBucketFolder -output burstyKeywordFile";

	private static String[] params = new String[] { INPUT, OUTPUT};

	WordFrequencyCLI(String[] args) {
		super(args, params, USAGE);
	}

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

	//private HashMap<String, Keyword> burstyMap;
	
	Gson gson = new Gson();

	public WordFrequencyCLI() {

	}

	public void init() {
		wordMap = new HashMap<String, Keyword>();
		//burstyMap = new HashMap<String, Keyword>();
	}

	public List<String> cleanTweetText(String tweetText) {

		// the replace all is to remove puntuation (breaks URLs)
		// List<String> words = new Text(tweetText.replaceAll("\\p{P}",
		// "").toLowerCase()).getTerms();
		// List<String> words = new
		// Text(tweetText.replaceAll("([a-z]+)[?:!.,;]*",
		// "$1").toLowerCase()).getTerms();

		// List<String> words = Twokenize.tokenize(tweetText);
		List<String> words = new ArrayList<String>();
		words = Twokenize.tokenizeRawTweetText(tweetText);
		List<String> cleanwords = new ArrayList<String>();
		

		if (words.size() > 0) {
			for (String s : words) {
				if (stopItalian.contains(s)) {
					continue;
				} else if (s.length() < 2) {
					continue;
				} else if (s.startsWith("@")) {
					continue;
				}
				cleanwords.add(s.toLowerCase());
			}
		}
		return cleanwords;
	}
	
	public List<String> cleanTweetTextNGrams(String tweetText) {

		List<String> words = new ArrayList<String>();
		// unigrams
		words = Twokenize.tokenizeRawTweetText(tweetText);
	
		// bigrams
		Reader reader = new StringReader(tweetText);
		TokenStream tokenizer = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
		tokenizer = new ShingleFilter(tokenizer, 2, 3);
		CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);

		try {
			while (tokenizer.incrementToken()) {
			    String token = charTermAttribute.toString();
			    if (StringUtils.split(token, " ").length > 1){
			    	words.add(token); // there will be unfiltered TODO
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		List<String> cleanwords = new ArrayList<String>();
			if (words.size() > 0) {
			for (String s : words) {
				if (stopItalian.contains(s)) {
					continue;
				} else if (s.length() < 2) {
					continue;
				} else if (s.startsWith("@")) {
					continue;
				}
				cleanwords.add(s.toLowerCase());
			}
		}
			
		for (String s : words) {
			System.out.println(s);
		}
			
		return cleanwords;
	}
	
	public List<String> cleanTweetTextNGramsDummy(String tweetText) {

		List<String> words = new ArrayList<String>();
		// unigrams
		words = Twokenize.tokenizeRawTweetText(tweetText);
		
		List<String> ngrams = new ArrayList<String>();
		
		//bigrams
		for(int x = 0; x < words.size()-1; x ++) {
			String s = words.get(x).concat(" ").concat(words.get(x+1));
			ngrams.add(s);
		}
		//trigrams
		for(int x = 0; x < words.size()-2; x ++) {
			String s = words.get(x).concat(" ").concat(words.get(x+1)).concat(" ").concat(words.get(x+2));
			ngrams.add(s);
		}
		words.addAll(ngrams);
		
		List<String> cleanwords = new ArrayList<String>();
			if (words.size() > 0) {
			for (String s : words) {
				if (stopItalian.contains(s)) {
					continue;
				} else if (s.length() < 2) {
					continue;
				} else if (s.startsWith("@")) {
					continue;
				} else if (s.contains("http://")){
					continue;
				}
				cleanwords.add(s.toLowerCase());
			}
		}
			
//		for (String s : words) {
//			System.out.println(s);
//		}
//		
//		System.out.println("================");
//		
//		for (String s : cleanwords) {
//			System.out.println(s);
//		}
//			
		return cleanwords;
	}

	public String getTimeinMilisFromFilename(String filename) {
		String[] elems = StringUtils.split(filename, "/");
		String timeinmilis = elems[elems.length - 1].replace(".json.gz", "");
		return timeinmilis;
	}

	public void countOccurences(String f, boolean isFirstFile)
			throws IOException, InvalidTweetException {

		BufferedReader bfr = IOUtils.getPlainOrCompressedUTF8Reader(f);

		String row;
		JsonTweet tweet;
		Keyword kw;
		
		String timestamp = getTimeinMilisFromFilename(f);
		
		while ((row = bfr.readLine()) != null) {
			tweet = JsonTweet.parseTweetFromJson(row);
			List<String> tweetWords = cleanTweetText(tweet.getText());

			for (String word : tweetWords) {
				if (wordMap.containsKey(word)) {
					kw = wordMap.get(word);
					kw.addBucketFrequency(timestamp, 1);
					kw.addUserId(tweet.getUsers().getId());
					kw.addTweetId(tweet.getId());
					if (isFirstFile){
						kw.addFirst50tweets(row);
					}
				} else {
					if (isFirstFile) {
						kw = new Keyword(word, timestamp, tweet.getId(), tweet.getUsers()
								.getId(), row);
						wordMap.put(word, kw);
					} else {
						// we do nothing here as there are words that are in
						// previous file and not in the
						// one we are analyzing for trends ; could be old
						// oudated hashtags etc.
					}
				}
			}

		}
		bfr.close();

	}

	public double getZScore(Keyword kw, int interval) {
		Iterator<Entry<String, Integer>> iterate = kw.getBucketFrequency()
				.entrySet().iterator();
		double baseFreq = (double) iterate.next().getValue();
		List<Double> frequencies = new ArrayList<Double>();
		double score;
//		SummaryStatistics stats = new SummaryStatistics();
//		while (iterate.hasNext()) {
//			stats.addValue(iterate.next().getValue());
//		}
//
//		double mean = stats.getMean();
//		double variance = stats.getVariance();
		
		// we do it like this because we need to divide by interval 
		// in case there aren't values for each day
		
		while (iterate.hasNext()) {
			frequencies.add((double)iterate.next().getValue());
		}
		
		double sum = 0.0;
	    for(double freq : frequencies)
	    	sum += freq;
	    double mean = sum/interval;
	    
	    double temp = 0;
	    for(double freq : frequencies)
	    	temp += (mean-freq)*(mean-freq);
	    double variance = temp/interval;

		
		// da gestire div by 0!
		if (variance == 0) {
			score = Double.MAX_VALUE;
		} else {
			score = (baseFreq - mean) / variance;
		}
		return score;
	}
	
	public String plainTrendFormatting(Keyword kw){
		String tweets = StringUtils.join(kw.getTweetIds(), "|");
		String users = StringUtils.join(kw.getUserIds(), "|");
		StringBuilder sb = new StringBuilder();
		sb.append(kw.getTrend()).append("\t").append(tweets).append("\t")
				.append(users).append("\t").append(kw.getZscore()).append("\t")
				.append(kw.getBasefreq());
	
		return sb.toString();
	}
	
	public String jsonTrendFormatting(Keyword kw){
		return gson.toJson(kw);
	}

	
	// there are words with high frequency but because they are repeated ina tweet
	// #bassil that may influence the statistics
							
	// the words that occur for the first time have zscore = 1.79...E308
	// meaning infinity - MAXINT
	public void writeDictionaryFreq(WordFrequencyCLI cli, int interval) {
		
		cli.openOutput();
		double baseFreq;
		double zscore;
		String time;
		for (Keyword kw : wordMap.values()) {
			
//			if (kw.getName().equals("#football")){
//				for ( Entry<String, Integer> s: kw.getBucketFrequency().entrySet()){
//					System.out.println(kw.getBucketFrequency().size());
//					System.out.println(s.getKey() + "\t" + s.getValue());
//				}
//			}
			
			baseFreq = (double) kw.getBucketFrequency().entrySet().iterator()
					.next().getValue();
			time = kw.getBucketFrequency().entrySet().iterator()
					.next().getKey();
			zscore = getZScore(kw, interval);
			
			kw.setBasefreq(baseFreq);
			kw.setZscore(zscore);
			kw.setTime(time);
			
			if ((Double.compare(zscore, 2.0d) >= 0) && (baseFreq > 10)) {
				//burstyMap.put(kw.getName(), kw);
				//cli.writeLineInOutput(plainTrendFormatting(kw));
				cli.writeLineInOutput(gson.toJson(kw));
				
			
			}
		}

	}

	public static void main(String[] args) throws InvalidTweetException,
			IOException {

		// we can do everything here

		WordFrequencyCLI cli = new WordFrequencyCLI(args);
		cli.init();
		
		File inputFolder = new File(cli.getInput());
		if (!inputFolder.exists()) {
			logger.error("cannot input dir {}", inputFolder.getAbsolutePath());
			System.exit(-1);
		}

		// 1. read the last 7 files
		File[] files = inputFolder.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				// we reverse the order
				return Long.valueOf(f2.lastModified()).compareTo(
						f1.lastModified());
			}
		});

		// 2. establish the length of the vector 7-10 buckets?
		int vectorLen = 7;

		// 3. we start with the last bucket and initialize the dictionary
		// wordMap
		// we use to count it on the one before last for safety (?!)
		File currentFile = files[0];
		logger.info("Processing file : {}", currentFile.getAbsolutePath());
		cli.countOccurences(currentFile.getAbsolutePath(), true);

		// 4. we search for those keywords TODO (uni,bi,trigrams) in the
		// previous buckets (should we keep different maps?)

		File[] previousFiles = Arrays.copyOfRange(files, 1, vectorLen + 1);
		for (File f : previousFiles) {
			try {
				logger.info("Processing file : {}", f.getAbsolutePath());
				cli.countOccurences(f.getAbsolutePath(), false);
			} catch (IOException e) {
				logger.error(
						"There is a problem with processing the current trends file - seed {}",
						currentFile.getName());
			}
		}

		// 5. check if they are bursty and put them in bursty map - after some
		// filters (configurable)  
		// DONE - this in done in cli.writeDictionaryFreq()
		
		// TODO 6. check for inclusions (uni,bi,tri):
		// if uni-bursty included in bi/tri-bursty, keep the longer occurence
		
		// 7. print all data, including tweetID and userID

		cli.writeDictionaryFreq(cli, vectorLen);

}

}
