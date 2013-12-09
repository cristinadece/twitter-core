///**
// *  Copyright 2012 Diego Ceccarelli
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
//import it.cnr.isti.hpc.twitter.util.TwitterProperties;
//import it.cnr.isti.hpc.twitter.util.Util;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Dict.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 09/gen/2012
// */
//public class DictSplitter  implements Splitter {
//	private static final Logger logger = LoggerFactory
//			.getLogger(DictSplitter.class);
//
//	// path of the camel hashtags file in the resources
//	public static final String DICT_FILE = "/segment/dict.txt.gz";
//
//	private Map<String, String> hashtag2seg;
//	private static DictSplitter stdDictSplitter;
//
//	
//	public DictSplitter() {
//		hashtag2seg = new HashMap<String, String>();
//	}
//
//	public void loadDict() {
//		
//		String dictFile = this.getClass()
//				.getResource(DICT_FILE).getPath().toString();
//		logger.info("reading dict");
//		BufferedReader br;
//		if (new File(dictFile).exists())
//			br = Util.getPlainOrCompressedReader(dictFile);
//		else
//			br = Util.getPlainOrCompressedReader(TwitterProperties.getInstance().getDict());
//		String line = "";
//		try {
//			while ((line = br.readLine()) != null) {
//				String ht = line.trim();
//				hashtag2seg.put(HashtagSegmenter.normalize(ht),ht);
//				
//			}
//		} catch (IOException e) {
//			logger.error("reading dict terms ({})",e.toString());
//			System.exit(-1);
//		}
//		logger.info("...done");
//		
//	}
//
//	public static DictSplitter getStandardDictSplitter(){
//		if (stdDictSplitter == null){
//			stdDictSplitter = new DictSplitter();
//			stdDictSplitter.loadDict();
//		}
//		return stdDictSplitter;
//	}
//	
//	public boolean contains(String key) {
//		return hashtag2seg.containsKey(HashtagSegmenter.normalize(key));
//	}
//
//	public String getSegmentation(String key) {
//		if (contains(key)) return hashtag2seg.get(HashtagSegmenter.normalize(key));
//		return "";
//	}
//
//
//}
