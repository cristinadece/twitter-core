///**
// * Copyright 2011 Diego Ceccarelli
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// * http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package it.cnr.isti.hpc.twitter.domain;
//
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//import it.cnr.isti.hpc.twitter.util.Multiset;
//import it.cnr.isti.hpc.twitter.util.TwitterProperties;
//import it.cnr.isti.hpc.twitter.util.Util;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.Iterator;
//import java.util.Scanner;
//
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.standard.StandardTokenizer;
//import org.apache.lucene.analysis.tokenattributes.TermAttribute;
//import org.apache.lucene.util.Version;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Terms.java Contains all the terms (not considering the hashtags) comparing in
// * a dataset with their occurrence
// * 
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 21/set/2011
// */
//public class Terms implements Iterable<String> {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory.getLogger(Terms.class);
//
//	private Multiset<String> multiset;
//
//	private static TokenStream tokenStream = new StandardTokenizer(
//	        Version.LUCENE_30, new StringReader(""));
//
//	public Terms() {
//		multiset = new Multiset<String>();
//	}
//
//	public Terms(String text) throws IOException {
//		this();
//		load(text);
//	}
//
//	public static Terms getInstance() {
//		return new Terms();
//	}
//
//	public static Terms load(String input) throws IOException {
//		Terms terms = Terms.getInstance();
//		try {
//			BufferedReader br = Util.getPlainOrCompressedReader(input);
//			String line = null;
//			logger.info("loading the terms from {} ...", input);
//			while ((line = br.readLine()) != null) {
//				Scanner s = new Scanner(line).useDelimiter("\t");
//				terms.add(s.next(), s.nextInt());
//			}
//			logger.info("...loaded");
//		} catch (IOException e) {
//			throw new IOException(
//			        "Error reading the hashtags from the input file");
//		}
//		return terms;
//	}
//
//	public static Terms load(String input, int minFreq) throws IOException {
//		Terms terms = Terms.getInstance();
//		try {
//			BufferedReader br = Util.getPlainOrCompressedReader(input);
//			String line = null;
//			logger.info("loading the terms from {} ...", input);
//			while ((line = br.readLine()) != null) {
//				Scanner s = new Scanner(line).useDelimiter("\t");
//				String key = s.next();
//				int value = s.nextInt();
//				if (value > minFreq)
//					terms.add(key, value);
//			}
//			logger.info("...loaded");
//		} catch (IOException e) {
//			throw new IOException(
//			        "Error reading the hashtags from the input file");
//		}
//		return terms;
//	}
//
//	public static Terms loadTrainingTerms() throws IOException {
//		Terms terms = getInstance();
//		// URL u =
//		// terms.getClass().getResource("/terms_training_set_small.txt");
//		terms = load(TwitterProperties.getInstance().getDict(), 3);
//		return terms;
//	}
//
//	public void clear() {
//		multiset.clear();
//	}
//
//	public void addTwit(Tweet t) throws IOException, InvalidTweetException {
//		addTerms(t.getTweetWithoutHashtags());
//	}
//
//	public void addBigramsTwit(Tweet t) throws IOException,
//	        InvalidTweetException {
//		addBigrams(t.getTweetWithoutHashtags());
//	}
//
//	public void addBigrams(String text) throws IOException {
//		((StandardTokenizer) tokenStream).reset(new StringReader(text));
//		TokenStream ts = (TokenStream) tokenStream;
//
//		TermAttribute termAttribute = ts.getAttribute(TermAttribute.class);
//		// add a triple for each of the tokens
//		String previousTerm = "";
//		try {
//			while (tokenStream.incrementToken()) {
//				String term = termAttribute.term();
//				if (!previousTerm.isEmpty())
//					multiset.add(previousTerm + " " + term);
//				previousTerm = term;
//
//			}
//		} catch (IOException e) {
//			throw new IOException("Error tokening the terms");
//		}
//
//	}
//
//	protected void addTerms(String text) throws IOException {
//		((StandardTokenizer) tokenStream).reset(new StringReader(text));
//		TokenStream ts = (TokenStream) tokenStream;
//
//		TermAttribute termAttribute = ts.getAttribute(TermAttribute.class);
//		// add a triple for each of the tokens
//		try {
//			while (tokenStream.incrementToken()) {
//				String term = termAttribute.term();
//				if (term.length() == 1)
//					continue;
//				char c = term.charAt(0);
//				if (Character.isLetterOrDigit(c)) {
//					multiset.add(term);
//				}
//			}
//		} catch (IOException e) {
//			throw new IOException("Error tokening the terms");
//		}
//
//	}
//
//	public void add(String hashtag) {
//		multiset.add(hashtag);
//	}
//
//	public void add(String hashtag, int freq) {
//		multiset.add(hashtag, freq);
//	}
//
//	public void remove(String hashtag) {
//		multiset.remove(hashtag);
//	}
//
//	public int getFrequency(String hashtag) {
//		return multiset.getValue(hashtag);
//	}
//
//	public boolean contains(String hashtag) {
//		return multiset.contains(hashtag);
//	}
//
//	public double getProbability(String hashtag) {
//		int val = multiset.getValue(hashtag);
//		if (val < 0)
//			return 0;
//		return val / (double) multiset.getTotal();
//	}
//
//	/**
//	 * returns a new hashtags set containing the intersection of the two
//	 * hashtags set. Frequencies are the frequencies of this object
//	 * 
//	 * @param ht
//	 *            the hashtags set to intersect
//	 * @return the intersection between this and ht
//	 */
//	public Terms intersect(Terms ht) {
//		Terms intersection = new Terms();
//		intersection.multiset = this.multiset.intersect(ht.multiset);
//		return intersection;
//
//	}
//
//	public void dump(String output) throws IOException {
//		try {
//			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
//
//			for (String ht : multiset) {
//				bw.write(ht);
//				bw.write("\t" + multiset.getValue(ht));
//				bw.write("\n");
//			}
//			bw.close();
//		} catch (IOException e) {
//			throw new IOException("Error writing the terms in the dump file");
//		}
//	}
//
//	public int getTotal() {
//		return multiset.getTotal();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see java.lang.Iterable#iterator()
//	 */
//	public Iterator<String> iterator() {
//		return multiset.iterator();
//	}
//
//	@Override
//	public String toString() {
//		return multiset.toString();
//	}
//
//	/**
//	 * @return
//	 * @throws IOException
//	 */
//	public static Terms loadDictionary() throws IOException {
//		Terms terms = getInstance();
//		terms = load(TwitterProperties.getInstance().getDict(), 3);
//		return terms;
//	}
//
//}
