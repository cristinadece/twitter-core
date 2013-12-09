//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import it.cnr.isti.hpc.twitter.util.Util;
//
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class EntropyContextual {
//	
//	public HashMap<String, Float> entropy;
//
//	private static final Logger logger = LoggerFactory
//			.getLogger(EntropyContextual.class);
//
//	public EntropyContextual() throws IOException {
//		entropy = new HashMap<String, Float>();
//	}
//
//	public int size() {
//		return entropy.size();
//	}
//
//	public static EntropyContextual loadMap() throws IOException {
//		BufferedReader bf = Util
//				.getPlainOrCompressedUTF8Reader("./src/main/resources/hashtags/entropy-contextual.tsv");
//		EntropyContextual map = new EntropyContextual();
//
//		String s = "";
//
//		while ((s = bf.readLine()) != null) {
//			// readline
//			String[] line = s.split("\t");
//			String hashtag = line[0];
//			Float entropyVal = Float.parseFloat(line[3]);
//
//			map.entropy.put(hashtag, entropyVal);
//		}
//		bf.close();
//		return map;
//
//	}
//
//	public float getEntropy(String s){
//		if (entropy.containsKey(s)){
//			return entropy.get(s);
//		}
//		else return 0.0f;
//		
//	}
//
//
//}
