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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import it.cnr.isti.hpc.twitter.util.Bigrams;
//import it.cnr.isti.hpc.twitter.util.IOUtils;
//import it.cnr.isti.hpc.twitter.util.TwitterProperties;
//import it.cnr.isti.hpc.twitter.util.Util;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
///**
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 29/set/2011
// */
//public class CamelHashtagSplitter implements Splitter {
//
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory
//			.getLogger(CamelHashtagSplitter.class);
//
//	// path of the camel hashtags file in the resources
//	public static final String CAMEL_HASHTAGS_FILE = "/segment/camel_hashtags.txt.gz";
//
//	private static CamelHashtagSplitter stdCamelHashtagSplitter = null;
//	private Map<String, String> hashtag2seg;
//
//	public CamelHashtagSplitter() {
//		hashtag2seg = new HashMap<String, String>();
//	}
//
//	public void addCamelHashtag(String camelHashtag) {
//		String segmentedCamelHashtag = segment(camelHashtag);
//		String normalizedCamelHashtag = HashtagSegmenter
//				.normalize(camelHashtag);
//		logger.debug(camelHashtag + "\t {} \t {} ", normalizedCamelHashtag,
//				segmentedCamelHashtag);
//		hashtag2seg.put(normalizedCamelHashtag, segmentedCamelHashtag);
//	}
//
//	private void loadSegmentedCamelHashtags() {
//
//		String camelHashtagFile = this.getClass()
//				.getResource(CAMEL_HASHTAGS_FILE).getPath().toString();
//		logger.info("reading training camel hashtags");
//		BufferedReader br;
//		if (new File(camelHashtagFile).exists())
//			br = IOUtils.getPlainOrCompressedReader(new File(camelHashtagFile).getPath());
//		else
//			br = Util.getPlainOrCompressedReader(TwitterProperties.getInstance().getProperty("camel.hashtags.file"));
//		
//		String line = "";
//		try {
//			while ((line = br.readLine()) != null) {
//				String ht = line.trim();
//				addCamelHashtag(ht);
//			}
//		} catch (IOException e) {
//			logger.error("reading the camel segmented hashtags ({})",
//					e.toString());
//			System.exit(-1);
//		}
//		logger.info("...done");
//
//	}
//
//	public static CamelHashtagSplitter getStandardCamelHashtagSplitter() {
//		if (stdCamelHashtagSplitter == null) {
//			stdCamelHashtagSplitter = new CamelHashtagSplitter();
//			stdCamelHashtagSplitter.loadSegmentedCamelHashtags();
//		}
//		return stdCamelHashtagSplitter;
//	}
//
//	// public static CamelHashtagSegmenter getInstance() throws IOException {
//	// if (hs == null)
//	// hs = new CamelHashtagSegmenter();
//	// return (CamelHashtagSegmenter) hs;
//	// }
//
//	/**
//	 * Segments a camel hashtags in terms.
//	 * 
//	 * @param hashtag
//	 *            the camel hashtag to segment
//	 * @return the segmented hashtag
//	 */
//	public String segment(String hashtag) {
//		boolean modified = false;
//
//		// Manage camel formats
//		if (isCamelEncoded(hashtag)) {
//			hashtag = normalizeCamelFormat(hashtag);
//			hashtag = decodeCamel(hashtag);
//			modified = true;
//		}
//
//		String tmp = decodeDigits(hashtag);
//		tmp = decodeUnderscore(tmp);
//		if (!(tmp.equals(hashtag)) || modified) {
//			tmp = tmp.toLowerCase();
//			return tmp;
//		}
//
//		return hashtag.toLowerCase();
//
//	}
//
//	private static String decodeUnderscore(String hashtag) {
//		return hashtag.replaceAll("_", " ");
//	}
//
//	protected String decodeDigits(String hashtag) {
//		StringBuilder sb = new StringBuilder();
//		int pos = 0;
//		char curChar = hashtag.charAt(pos);
//		int currentType = Character.getType(curChar);
//		sb.append(curChar);
//		pos++;
//		while (pos < hashtag.length()) {
//			curChar = hashtag.charAt(pos);
//			int type = Character.getType(curChar);
//			if ((type == Character.DECIMAL_DIGIT_NUMBER)
//					&& (currentType != Character.DECIMAL_DIGIT_NUMBER))
//				if (curChar != ' ')
//					sb.append(' ');
//			if ((type != Character.DECIMAL_DIGIT_NUMBER)
//					&& (currentType == Character.DECIMAL_DIGIT_NUMBER))
//				if (curChar != ' ')
//					sb.append(' ');
//			currentType = type;
//			sb.append(curChar);
//			pos++;
//		}
//		return sb.toString();
//	}
//
//	private static String decodeCamel(String hashtag) {
//		StringBuilder sb = new StringBuilder();
//		int pos = 0;
//		char curChar = hashtag.charAt(pos);
//		int currentType = Character.getType(curChar);
//		sb.append(curChar);
//		pos++;
//		while (pos < hashtag.length()) {
//			curChar = hashtag.charAt(pos);
//			int type = Character.getType(curChar);
//			if ((type == Character.UPPERCASE_LETTER))
//				sb.append(" ");
//			currentType = type;
//			sb.append(curChar);
//			pos++;
//		}
//		return sb.toString();
//
//	}
//
//	/**
//	 * if the string contains sequences of UPPERCASE chars longer then 3 (e.g.
//	 * imDaTypeOfPERSON) normalize them putting in lowercase the Uppercase chars
//	 * after the first (imDaTypeOfPerson)
//	 * 
//	 * @param hashtag
//	 * @return
//	 */
//	private String normalizeCamelFormat(String hashtag) {
//		final int MAX_LEN = 3;
//		StringBuilder sb = new StringBuilder();
//		int pos = 0;
//		int uppercaseChar = 0;
//		String buffer = "";
//		char curChar;
//		while (pos < hashtag.length()) {
//			curChar = hashtag.charAt(pos);
//			int type = Character.getType(curChar);
//			if ((type == Character.UPPERCASE_LETTER)) {
//				buffer += curChar;
//				uppercaseChar++;
//			} else {
//				if (uppercaseChar >= MAX_LEN) {
//					sb.append(buffer.charAt(0));
//					buffer = buffer.substring(1);
//					sb.append(buffer.toLowerCase());
//					buffer = "";
//				}
//				sb.append(buffer);
//				buffer = "";
//				uppercaseChar = 0;
//				sb.append(curChar);
//			}
//			pos++;
//		}
//		if (uppercaseChar >= MAX_LEN) {
//			sb.append(buffer.charAt(0));
//			buffer = buffer.substring(1);
//			sb.append(buffer.toLowerCase());
//			buffer = "";
//		}
//		sb.append(buffer);
//		return sb.toString();
//	}
//
//	public static boolean isCamelEncoded(String hashtag) {
//		boolean lowercase = false;
//		boolean uppercase = false;
//		for (int i = 0; i < hashtag.length(); i++) {
//			char curChar = hashtag.charAt(i);
//			int type = Character.getType(curChar);
//			if (type == Character.LOWERCASE_LETTER)
//				lowercase = true;
//			if (type == Character.UPPERCASE_LETTER)
//				uppercase = true;
//			if (lowercase && uppercase)
//				return true;
//		}
//		return false;
//	}
//
//	public boolean contains(String hashtag) {
//		return hashtag2seg.containsKey(HashtagSegmenter.normalize(hashtag));
//	}
//
//	public String getSegmentation(String hashtag) {
//		if (contains(hashtag))
//			return hashtag2seg.get(HashtagSegmenter.normalize(hashtag));
//		else
//			return "";
//	}
//
//}
