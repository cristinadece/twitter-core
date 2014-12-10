/**
 * 
 */
package it.cnr.isti.hpc.twitter.trends;

import it.cnr.isti.hpc.twitter.util.Text;
import it.cnr.isti.hpc.twitter.util.Twokenize;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * @author cris
 *
 */
public class CleanAndTokenizeTweet {
	
	private final static List<String> stopItalian = Arrays.asList(new String[] {
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


	/**
	 * 
	 */
	public CleanAndTokenizeTweet() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static List<String> cleanTweetTextRawTokenizer(String tweetText) {

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
	
	public static List<String> cleanTweetTextTokenizer(String tweetText) {

		List<String> words = new ArrayList<String>();
		words = Twokenize.tokenize(tweetText);
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
	
	public static List<String> cleanTweetTextRegex(String tweetText) {

		// the replace all is to remove puntuation (breaks URLs)
		List<String> words = new Text(tweetText.replaceAll("\\p{P}", "").toLowerCase()).getTerms();
		//List<String> words = new Text(tweetText.replaceAll("([a-z]+)[?:!.,;]*", "$1").toLowerCase()).getTerms();
		
		
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

	public static List<String> cleanTweetTextNGrams(String tweetText) {

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
	
	public static List<String> cleanTweetTextNGramsDummy(String tweetText) {

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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
