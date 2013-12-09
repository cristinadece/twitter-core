//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import it.cnr.isti.hpc.twitter.domain.prediction.Result;
//import it.cnr.isti.hpc.twitter.util.Multiset;
//import it.cnr.isti.hpc.twitter.util.Multiset.KeyAndFreq;
//import it.cnr.isti.hpc.twitter.util.Util;
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
//public class ContextualGivenInline {
//	public HashMap<String,Multiset<String>> contextual ;
//
//	private static final Logger logger = LoggerFactory
//			.getLogger(Hashtags.class);
//	
//	public ContextualGivenInline() throws IOException{
//		contextual = new HashMap<String,Multiset<String>>();
//
//	}
//	
//	public int size() {
//		return contextual.size();
//		}
//	
//	public static ContextualGivenInline loadMap() throws IOException{
//		BufferedReader bf = Util.getPlainOrCompressedUTF8Reader("./src/main/resources/hashtags/inline_context.tsv");
//		ContextualGivenInline map = new  ContextualGivenInline();
//		
//		String s = "";
//		
//			while ((s = bf.readLine()) != null) {
//				//readline
//				String[] line = s.split("\t");
//				String inline = line[0];
//				String context = line[1];
//				int freq = Integer.parseInt(line[2]);
//				
//				
//				//add contextual to a pre-existing Inline
//				if (map.contextual.containsKey(inline)){
//					Multiset<String> contextSet = map.contextual.get(inline);
//					contextSet.add(context, freq);					
//				}
//				
//				//add Inline and contextual
//				else {
//					Multiset<String> contextSet = new Multiset<String>();
//					contextSet.add(context, freq);
//					map.contextual.put(inline, contextSet);
//				}
//			}
//			bf.close();
//			return map;
//		
//	}
//
//	
//	public List<Result> getOrderedContextual(String s){
//		
//		List<Result> result = new ArrayList<Result>();
//				
//		if (contextual.containsKey(s)){
//			Multiset value = contextual.get(s);
//			List<KeyAndFreq> orderedContext = value.sortByFrequency();
//			int total = value.getTotal();
//			
//			
//			for (KeyAndFreq item : orderedContext){
//				float probability = (float) item.getFreq()/total;
//				String contextHashtag = (String) item.getKey();
//				Result res = new Result(contextHashtag, probability);
//				result.add(res);				
//			}
//			return result;
//			
//		}
//		else {return result;}
//	}
//	
//}
