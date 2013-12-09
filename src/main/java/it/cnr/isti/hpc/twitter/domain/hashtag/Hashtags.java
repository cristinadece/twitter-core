///**
// *  Copyright 2011 Diego Ceccarelli
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import it.cnr.isti.hpc.twitter.domain.Tweet;
//import it.cnr.isti.hpc.twitter.util.IOUtils;
//import it.cnr.isti.hpc.twitter.util.Multiset;
//import it.cnr.isti.hpc.twitter.util.TwitterProperties;
//import it.cnr.isti.hpc.twitter.util.Util;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.InputMismatchException;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.PriorityQueue;
//import java.util.Scanner;
//import java.util.Set;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import org.apache.lucene.search.Sort;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Hashtags contains stats and info about hashtags comparing in a dataset
// * (training/test). More in detail for each hashtag the following info are
// * available:
// * <ul>
// * <li>if it is marked as spam</li>
// * <li>its segmentation (e.g., followfriday -> follow friday), note that for the
// * most frequent hashtags like ff, the segmentation is expanded e.g. -> follow
// * friday</li>
// * <li>if is present in the training set / test set</li>
// * <li>top k hashtags (in training / in test / in both</li>
// * <li>freq of an hashtag</li>
// * <li>probability to be a inline/contextual given an hashtag</li>
// * <li>most probable cooccurring hashtag</li>
// * </ul>
// * 
// * 
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 21/set/2011
// */
//public class Hashtags {
//	// implements Iterable<Hashtag>, Serializable {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory
//			.getLogger(Hashtags.class);
//
//	// public static final String
//	// TRAINING_HASHTAGS="/hashtags/distinct_hashtags_frequencies_over_the_training_with_frequence_geq_6.tsv.gz";
//
//	private static Hashtags instance;
//	
//	
//	private SortedSet<Hashtag> hashtags;
//	private Hashtags trainingHashtags;
//	private Hashtags testHashtags;
//	private int totalFrequency = 0;
//	
//	
//	private static Map<String, Hashtag> map;
//
//	private Hashtags() {
//		
//		hashtags = new TreeSet<Hashtag>();
//	}
//	
//	private void load(){
//		map = new HashMap<String,Hashtag>();
//		hashtags = new TreeSet<Hashtag>();	
//		loadHashtagsDataset();
//		loadTrainingAndTestHashtags();
//		hashtags = new TreeSet<Hashtag>(map.values());	
//	}
//
//	private void loadHashtagsDataset() {
//		String path = TwitterProperties.getInstance().getProperty(
//				"hashtag.dataset");
//		BufferedReader br = IOUtils.getPlainOrCompressedUTF8Reader(path);
//
//		try {
//
//			for (String line = br.readLine(); line != null; line = br
//					.readLine()) {
//				try{
//				Scanner scanner = new Scanner(line).useDelimiter("\t");
//				String hashtag = scanner.next();
//				int context = scanner.nextInt();
//				scanner.next();
//				int inline = scanner.nextInt();
//				scanner.next();
//				scanner.next();
//				boolean isSpam = scanner.next().equals("1");
//				int type = scanner.nextInt();
//				String segmentedHashtag = scanner.next().toLowerCase();
//				Hashtag ht = add(hashtag);
//				ht.setContextFrequency(context);
//				ht.setInlineFrequency(inline);
//				ht.setSpam(isSpam);
//				if (!segmentedHashtag.equals("null"))
//					ht.setSegmentedHashtag(segmentedHashtag);
//		
//				}catch (InputMismatchException e ){
//					logger.error("line {}",line);
//				}
//
//			}
//		} catch (IOException e) {
//			logger.error("reading the hashtag dataset {} ", e.toString());
//		} 
//	}
//	
//	public static Hashtags getInstance(){
//		if (instance != null) return instance;
//		instance = new Hashtags();
//		instance.load();
//		return instance;
//	}
//	
//	public Hashtags getTrainingHashtags(){
//		if (trainingHashtags != null) return trainingHashtags;
//		trainingHashtags = new Hashtags();
//		
//		for (Hashtag h : hashtags){
//			if (h.getTrainingFrequency() > 0){
//				trainingHashtags.hashtags.add(h);
//				trainingHashtags.totalFrequency+=h.getFrequency();
//			}
//		}
//		return trainingHashtags;
//	}
//	public Hashtags getTestHashtags(){
//		if (testHashtags != null) return testHashtags;
//		testHashtags = new Hashtags();
//		for (Hashtag h : hashtags){
//			if (h.getTestFrequency() > 0){
//				testHashtags.hashtags.add(h);
//				testHashtags.totalFrequency+=h.getFrequency();
//			}
//		}
//		return testHashtags;
//	}
//	
//	public double getTrainingProbability(String ht){
//		if (map.containsKey(ht)){
//			return map.get(ht).getTrainingFrequency()/(double)totalFrequency;
//		}
//		return 0d;
//	}
//	
//	public boolean contains(String ht){
//		return map.containsKey(ht);
//	}
//	
//	public List<Hashtag> getTopTrainingHashtags(int k){
//		List<Hashtag> topHashtags = new ArrayList<Hashtag>();
//		Hashtags s = getTrainingHashtags();
//		for (Hashtag ht : s.hashtags){
//			if (ht.isSpam()) continue;
//			topHashtags.add(ht);
//			k--;
//			if (k==0) break;
//		}
//		Collections.sort(topHashtags, new SortByTrainingFreq());
//		return topHashtags;
//	}
//
//	private void loadTrainingAndTestHashtags() {
//		String path = TwitterProperties.getInstance().getProperty(
//				"hashtag.training.test");
//		BufferedReader br = IOUtils.getPlainOrCompressedUTF8Reader(path);
//		logger.info("loading training and test");
//		int i = 0;
//		try {
//			for (String line = br.readLine(); line != null; line = br
//					.readLine()) {
//				if (i++ % 10000 == 0) System.out.print(i+" ");
//				if (line.charAt(0) == '#') continue;
//				Scanner scanner = new Scanner(line).useDelimiter("\t");
//				String hashtag = scanner.next();
//				int trainingFrequency = scanner.nextInt();
//				int testFrequency = scanner.nextInt();
//				Hashtag h = add(hashtag);
//				h.setTrainingFrequency(trainingFrequency);
//				h.setTestFrequency(testFrequency);
//				h.setFrequency(trainingFrequency+testFrequency);
//				totalFrequency += trainingFrequency+testFrequency;
//				
//				
//			}
//		} catch (IOException e) {
//			logger.error("reading the hashtag dataset {} ", e.toString());
//		}
//
//	}
//	
//
//	private Hashtag add(Hashtag ht) {
//		return add(ht.getHashtag());
//	}
//
//	private Hashtag add(String ht) {
//		if (map.containsKey(ht)) {
//			return map.get(ht);
//		}
//		Hashtag h = new Hashtag(ht);
//		map.put(ht, h);
//		//hashtags.add(h);
//		return h;
//	}
//	
//	public Hashtag get(String ht){
//		if  (map.containsKey(ht)) {
//			return map.get(ht);
//		}
//		return new Hashtag(ht);
//	}
//
//	
//	public static void main(String[] args) {
//		Hashtags t = new Hashtags();
//
//	}
//	
//	public static class SortByTrainingFreq implements Comparator<Hashtag>{
//
//		public int compare(Hashtag arg0, Hashtag arg1) {
//			return arg1.getTrainingFrequency() - arg0.getTrainingFrequency();
//		}
//		
//	}
//	
//	public static class SortByInlineFreq implements Comparator<Hashtag>{
//
//		public int compare(Hashtag arg0, Hashtag arg1) {
//			return arg0.getInlineFrequency() - arg1.getInlineFrequency();
//		}
//		
//	}
//	
//	
//}
