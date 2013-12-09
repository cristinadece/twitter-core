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
//package it.cnr.isti.hpc.twitter.domain.hashtag;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * HashtagSegmenter.java
// * 
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 22/set/2011
// */
//public class HashtagSegmenter {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory
//	        .getLogger(HashtagSegmenter.class);
//
//	public static HashtagSegmenter standardSegmenter = null;
//	List<Splitter> splitters = new ArrayList<Splitter>();
//	private int total; // number of total requests
//	private int hits; // number of hits
//	
//	public HashtagSegmenter() {
//		total = 0;
//		hits = 0;
//	}
//	
//	public void addSplitter(Splitter s){
//		splitters.add(s);
//	}
//	
//	
//	
//	public String segment(String text){
//		total++;
//		for (Splitter s : splitters){
//			if (s.contains(text)){
//				hits++;
//				return s.getSegmentation(text);
//			}
//		}
//		return "";
//	}
//	
//	public boolean contains(String text){
//		for (Splitter s : splitters){
//			if (s.contains(text)) return true;
//		}
//		return false;
//	}
//	
//	public static HashtagSegmenter getStandardSegmenter(){
//		if (standardSegmenter == null) {
//			standardSegmenter = new HashtagSegmenter();
//			DictSplitter dict = DictSplitter.getStandardDictSplitter();
//			CamelHashtagSplitter chs = CamelHashtagSplitter.getStandardCamelHashtagSplitter();
//			standardSegmenter.addSplitter(dict);
//			standardSegmenter.addSplitter(chs);
//			
//			
//		}
//		return standardSegmenter;
//	}
//	
//	public float hitRate(){
//		return hits/(float)total;
//	}
//	
//	
//	public static String normalize(String s){
//		s = s.replaceAll(" ","");
//		s = s.replaceAll("_","");
//		s = s.replaceAll("-","");
//		return s.toLowerCase();
//	}
//
//	
//
//
//}
