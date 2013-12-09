//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import it.cnr.isti.hpc.twitter.domain.prediction.Result;
//import it.cnr.isti.hpc.twitter.util.MultisetFloat.KeyAndProbab;
//import it.cnr.isti.hpc.twitter.util.MultisetFloat;
//import it.cnr.isti.hpc.twitter.util.Util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class CooccurrenceProbability {
//	public HashMap<String, MultisetFloat<String>> cooccurrence;
//
//	private static final Logger logger = LoggerFactory
//			.getLogger(Hashtags.class);
//
//	public CooccurrenceProbability() throws IOException {
//		cooccurrence = new HashMap<String, MultisetFloat<String>>();
//	}
//
//	public int size() {
//		return cooccurrence.size();
//	}
//
//	public static CooccurrenceProbability loadMap() throws IOException {
//		BufferedReader bf = Util
//				.getPlainOrCompressedUTF8Reader("./src/main/resources/hashtags/h2givenh1-training.tsv");
//		CooccurrenceProbability map = new CooccurrenceProbability();
//
//		float[] removals = {(float) 1.0, (float)0.5f, (float)0.25, (float)0.33333 };
//
//		String s = "";
//
//		while ((s = bf.readLine()) != null) {
//			// readline
//			String[] line = s.split("\t");
//			String probableHashtag = line[0];
//			String givenHashtag = line[1];
//			float probability = Float.parseFloat(line[2]);
//
//			// add probableHashtag to a pre-existing givenHashtag
//			if (map.cooccurrence.containsKey(givenHashtag)) {
//				MultisetFloat<String> contextSet = map.cooccurrence
//						.get(givenHashtag);
//				for (float f : removals){
//					if (Float.compare(f, probability)==0){
//						break;
//					}
//					else{
//					contextSet.add(probableHashtag, probability);
//					break;
//					}
//				}
//			}
//
//			// add givenHashtag with probablehashtag
//			else {
//				MultisetFloat<String> contextSet = new MultisetFloat<String>();
//				//if (!Arrays.asList(removals).contains(probability)) {
//				//	contextSet.add(probableHashtag, probability);
//				//}
//				for (float f : removals){
//					if (Float.compare(f, probability)==0){
//						break;
//					}
//					else{
//					contextSet.add(probableHashtag, probability);
//					break;
//					}
//				}
//				map.cooccurrence.put(givenHashtag, contextSet);
//			}
//		}
//		bf.close();
//		return map;
//
//	}
//
//	public List<Result> getOrderedProbableHashtags(String s) {
//		List<Result> result = new ArrayList<Result>();
//		
//		if (cooccurrence.containsKey(s)) {
//			MultisetFloat value = cooccurrence.get(s);
//			List<KeyAndProbab> orderedProbableHashtags = value
//					.sortByFrequency();
//			// float total = value.getTotal();
//
//			for (KeyAndProbab item : orderedProbableHashtags) {
//				float probability = (float) item.getProbab();
//				String probableHashtag = (String) item.getKey();
//				Result res = new Result(probableHashtag, probability);
//				result.add(res);
//			}
//			return result;
//
//		} else {
//			
//			return result;
//		}
//	}
//
//}
