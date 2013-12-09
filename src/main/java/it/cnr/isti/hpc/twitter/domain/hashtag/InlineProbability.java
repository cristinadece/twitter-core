//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import it.cnr.isti.hpc.twitter.util.Util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.HashMap;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class InlineProbability {
//	private static final Logger logger = LoggerFactory
//			.getLogger(Hashtags.class);
//	
//	public HashMap<String,Float> inlineProb ;
//	
//	public InlineProbability() throws IOException{
//		inlineProb = new HashMap<String,Float>();
//
//	}
//	
//	public int size() {
//		return inlineProb.size();
//		}
//	
//	public static InlineProbability loadMap() throws IOException{
//		BufferedReader bf = Util.getPlainOrCompressedUTF8Reader("./src/main/resources/hashtags/inline_probability.tsv");
//		InlineProbability map = new  InlineProbability();
//		
//		String s = "";
//		
//			while ((s = bf.readLine()) != null) {
//				//read line
//				String[] line = s.split("\t");
//				String inline = line[0];
//				float probability = Float.parseFloat(line[1]);
//							
//				
//				//add inline & probab to map
//				//if (!map.inlineProb.containsKey(inline)){
//					map.inlineProb.put(inline, probability);					
//				//}
//				
//				
//			}
//			bf.close();
//			return map;
//		
//	}
//
//	
//	public float getInlineProbability(String s){
//				
//		if (inlineProb.containsKey(s)){
//			return inlineProb.get(s);
//			
//		}
//		else {return 0.0f;}
//	}
//}
