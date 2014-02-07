/**
 *  Copyright 2012 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package it.cnr.isti.hpc.twitter.domain;

import it.cnr.isti.hpc.twitter.util.Text;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * BasicTweet.java
 * 
 * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 09/gen/2012
 */
public abstract class BasicTweet implements Tweet {

	public static Gson gson = new Gson();
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BasicTweet.class);

	protected String text; // text of the twit

	public String getText() {
		return text;

	}

	public boolean isEnglish() {
		return getLanguage().equals("en");
	}

	public boolean isItalian() {
		return getLanguage().equals("it");
	}

	public String getLanguage() {
		return Text.getLanguage(text);
	}

	public String getStemmedText() {
		StringBuffer result = new StringBuffer();
		if (text != null && text.trim().length() > 0) {
			StringReader tReader = new StringReader(text);
			// FIXME using the english stemmer
			Analyzer analyzer = new SnowballAnalyzer(Version.LUCENE_34,
					"English", Collections.emptySet());
			TokenStream tStream = analyzer.tokenStream("contents", tReader);
			TermAttribute term = tStream.addAttribute(TermAttribute.class);

			try {
				while (tStream.incrementToken()) {
					result.append(term.term());
					result.append(' ');
				}
			} catch (IOException ioe) {
				logger.error(ioe.toString());
			}
		}

		// If, for some reason, the stemming did not happen, return the original
		// text
		if (result.length() == 0) {
			logger.warn("stemming not performed for {}", text);
			result.append(text);
		}
		return result.toString().trim();
	}

	public List<String> getInLineHashtags() {
		List<String> hashtags = new LinkedList<String>(getHashtagsList());
		hashtags.removeAll(getContextHashtags());
		return hashtags;
	}

	public List<String> getContextHashtags() {

		String characts = ".,/?;:|!";

		String tweetText = getText();

		String[] urlsCheck = getText().split("[ 	]");

		for (String s : urlsCheck) {
			if (!s.isEmpty()) {

				try {
					@SuppressWarnings("unused")
					URL url = new URL(s);
					tweetText = tweetText.replace(s, "");
				} catch (MalformedURLException e) {
					// System.out.println("Nu e url" + s);
				}

			}
		}

		/*
		 * for (String s : tmp){ System.out.print(s + " "); }
		 * System.out.println();
		 */
		String[] tmp = tweetText.split("[ 	]");

		List<String> terms = new LinkedList<String>();
		for (int i = tmp.length - 1; i >= 0; i--) {
			if (tmp[i].isEmpty())
				continue;
			if (tmp[i].charAt(0) == '#') {
				// see if hashtags is in list if not disconsider... if yes add
				// to list

				if (getHashtagsList().contains(
						tmp[i].substring(1).toLowerCase())) {
					terms.add(0, tmp[i].substring(1).toLowerCase());
				}
				// split .;!?
				else {
					String lastLetter = String.valueOf(tmp[i].charAt(tmp[i]
							.length() - 1));

					if (characts.contains(lastLetter)) {
					} else {
						String[] tmp2 = tmp[i].split("[.,/!?;|]");
						int len = tmp2.length - 1;
						if (tmp2[len].charAt(0) == '#') {
							if (getHashtagsList().contains(
									tmp2[len].substring(1).toLowerCase())) {
								terms.add(0, tmp2[len].substring(1)
										.toLowerCase());
							}

						} else
							break;
					}
				}

			} else
				break;
		}
		return terms;
	}

	// public String getTextWithoutContextualHashtags() {
	//
	// // get the text
	//
	// String tweetText = getText().toLowerCase();
	//
	// // remove URLS & contextual hashtags
	//
	// String[] items = tweetText.split("[ 	]");
	// for (String s : items) {
	//
	// if (!s.isEmpty()) {
	//
	// // URL
	// try {
	// @SuppressWarnings("unused")
	// URL url = new URL(s);
	// tweetText = tweetText.replace(s, "");
	// } catch (MalformedURLException e) {
	//
	// }
	//
	// // contextual
	//
	// List<String> contextuals = getContextHashtags();
	// for (String contextHt : contextuals) {
	// StringBuilder sb = new StringBuilder();
	// sb.append("#").append(contextHt);
	// tweetText = tweetText.replace(sb.toString(), "");
	// }
	// }
	//
	// }
	//
	// // remove mentions
	// tweetText = getTextWithoutMentions(tweetText);
	//
	// // replace inline hashtags with segmented version
	//
	// HashtagSegmenter segm = HashtagSegmenter.getStandardSegmenter();
	// List<String> inlineHashtags = getInLineHashtags();
	//
	// for (String hashtag : inlineHashtags) {
	// String splitHashtag = segm.segment(hashtag);
	// if (!splitHashtag.isEmpty()) {
	// tweetText = tweetText.replaceAll(hashtag, splitHashtag);
	// }
	// }
	//
	// // see if
	//
	// if (tweetText.isEmpty()) {
	// return tweetText;
	// }
	// // rebuild tweet text???
	// else {
	// Text text = new Text(tweetText);
	// StringBuilder cleanTweet = new StringBuilder();
	// for (String words : text.getTerms()) {
	// cleanTweet.append(words).append(" ");
	// }
	// return cleanTweet.toString().trim();
	// }
	//
	// }

	// public String getTextWithoutOneContextualHashtag(String contextualHt) {
	//
	// // get the text
	//
	// String tweetText = getText().toLowerCase();
	//
	// // remove URLS
	//
	// String[] items = tweetText.split("[ 	]");
	// for (String s : items) {
	//
	// if (!s.isEmpty()) {
	//
	// // URL
	// try {
	// @SuppressWarnings("unused")
	// URL url = new URL(s);
	// tweetText = tweetText.replace(s, "");
	// } catch (MalformedURLException e) {
	//
	// }
	//
	// }
	//
	// }
	//
	// // remove contextual
	//
	// StringBuilder sb = new StringBuilder();
	// sb.append("#").append(contextualHt);
	// tweetText = tweetText.replace(sb.toString(), "");
	//
	// // remove mentions
	// tweetText = getTextWithoutMentions(tweetText);
	//
	// // replace inline hashtags with segmented version
	//
	// HashtagSegmenter segm = HashtagSegmenter.getStandardSegmenter();
	// List<String> inlineHashtags = getInLineHashtags();
	//
	// for (String hashtag : inlineHashtags) {
	// String splitHashtag = segm.segment(hashtag);
	// tweetText = tweetText.replaceAll(hashtag, splitHashtag);
	// }
	//
	// // see if
	//
	// if (tweetText.isEmpty()) {
	// return tweetText;
	// }
	//
	// // rebuild tweet text???
	// else {
	// Text text = new Text(tweetText);
	// StringBuilder cleanTweet = new StringBuilder();
	// for (String words : text.getTerms()) {
	// cleanTweet.append(words).append(" ");
	// }
	// return cleanTweet.toString().trim();
	// }
	//
	// }

	// public String getSegmentedHashtags() {
	//
	// HashtagSegmenter segm = HashtagSegmenter.getStandardSegmenter();
	// List<String> hashtagList = getHashtagsList();
	// StringBuilder segmHashtags = new StringBuilder();
	//
	// for (String hashtag : hashtagList) {
	// String splitHashtag = segm.segment(hashtag);
	// segmHashtags.append(splitHashtag).append(" ");
	// }
	// return segmHashtags.toString().trim();
	// }

	public abstract long getDateInMilliseconds();

	public abstract List<String> getHashtagsList();

	public abstract List<String> getRawHashtagsList();

	public List<String> getTerms() {
		Text t = new Text(getText());

		return t.getTerms();

	}

	public boolean hasHashtags() {
		return getHashtagsList().size() > 0;
	}

	public String getTweetWithoutHashtags() {
		String text = getText().toLowerCase();
		text = text.replaceAll("#[^ .]+", " ");
		return text;
	}

	public String getTweetWithoutMentions() {
		String text = getText().toLowerCase();
		text = text.replaceAll("@[^ .]+", " ");
		return text;
	}

	public String getTextWithoutMentions(String s) {
		s = s.replaceAll("@[^ ]+", " ");
		return s;
	}

	public static boolean isRetweet(String text) {
		return text.toLowerCase().startsWith("rt");
	}

	public boolean isRetweet() {
		return isRetweet(getText());
	}

	public boolean isOnlyURL() {
		String[] items = getText().split("[ 	]");
		int i = 0;
		for (String s : items) {
			if (!s.isEmpty()) {
				try {
					URL url = new URL(s);
					i++;
				} catch (MalformedURLException e) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isOnlyURLAndHashtags() {

		String[] items = getText().trim().replaceAll("[ 	]+", " ").split(" ");

		int i = 0;
		int j = 0;
		for (String s : items) {
			if (!s.isEmpty()) {
				try {
					@SuppressWarnings("unused")
					URL url = new URL(s);
					i++;
				} catch (MalformedURLException e) {
				}

				if (s.charAt(0) == '#') {
					j++;
				}
			}
		}

		if ((i > 0) && (j > 0) && (i + j == items.length)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasUrl() {
		String[] items = getText().split("[ 	]");
		int i = 0;
		for (String s : items) {
			if (!s.isEmpty()) {
				try {
					@SuppressWarnings("unused")
					URL url = new URL(s);
					i++;
				} catch (MalformedURLException e) {

				}
			}
		}

		return i > 0;
	}

	public boolean hasMentions() {
		String[] items = getText().split("[ 	]");
		for (String s : items) {

			if ((s.charAt(0) == '@') && (s.length() > 1)) {
				// System.out.println("|"+s+"|");
				return true;
			}
		}

		return false;
	}

	public static boolean beginsWithMentions(String text) {
		String[] items = text.trim().split("[ 	]");
		if ((items[0].charAt(0) == '@') && (items[0].length() > 1)) {
			return true;
		} else {

			return false;
		}
	}

	public boolean beginsWithMentions() {
		return beginsWithMentions(getText());
	}

	/**
	 * verifies if a tweet is <i> legal </i> i.e., if it contains at least an
	 * alphabetic char
	 * 
	 * @param twit
	 *            - the text of the twit
	 * @return true, if the twit contains at least an alphabetic char, otherwise
	 *         false
	 */
	protected static boolean isLegal(String tweet) {
		tweet = tweet.toLowerCase();
		int i = 0;
		while (i < tweet.length()) {
			if ((tweet.charAt(i) >= 'a') && (tweet.charAt(i) <= 'z')) {
				return true;
			}
			i++;
		}
		return false;
	}

	/**
	 * Clean tweet text (used to query solr with the text of a twit), remove all
	 * not alphanumeric chars (leaving the spaces)
	 * 
	 * @param query
	 *            - a string of text
	 * @return only alphanumeric chars and spaces in text
	 */
	protected static String cleanQuery(String query) {
		query = query.replaceAll("[^a-zA-Z0-9]", " ");
		return query.trim();
	}

	/**
	 * verifies if text of a tweet is <i> legal </i> i.e., if it contains at
	 * least an alphabetic char
	 * 
	 * @return true if is a regular twit
	 */
	public boolean isLegal() {
		if (text == null)
			return false;
		return isLegal(text);
	}

	// public String getIndexLine() {
	//
	// StringBuilder sb = new StringBuilder();
	// String cleanText = getCleanTweet();
	//
	// for (String hashtag : getHashtagsList()) {
	//
	// sb.append(hashtag).append("\t").append(getText()).append("\t")
	// .append(getRelatedHashtags(hashtag)).append("\t");
	// sb.append(cleanText).append("\t");
	// for (String t : getHashtagsList()) {
	// sb.append(Hashtags.getInstance().get(t).getSegmentedHashtag())
	// .append(", ");
	// }
	// sb.append("\n");
	// }
	// return sb.toString();
	// }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(text).append("\t");
		for (String s : getRawHashtagsList())
			sb.append(s).append(" ");

		sb.append("\t").append(getDateInMilliseconds());
		return sb.toString();
	}

	/**
	 * Given an hashtag in the tweet, returns a string containing other hashtags
	 * contained in the twit.
	 * 
	 * @param hashtag
	 *            - the hashtag selected
	 * @return the other hashtags
	 */
	public String getRelatedHashtags(String hashtag) {
		StringBuilder sb = new StringBuilder();

		for (String s : getRawHashtagsList())
			if (!s.equalsIgnoreCase(hashtag)) {
				sb.append(s).append(" , ");
			}
		return sb.toString();

	}

	/**
	 * returns the the text of the tweet, in order to us it as a query to solr
	 * to get the suggestion. If the tweet contains hastags, the sharp is
	 * removed and the text of the hashtags is splitted in terms. NOTE:
	 * contextual hashtags are removed
	 */
	public String getCleanTweet() {
		// Hashtags hashtags = Hashtags.getInstance();
		// String tweetText = getTweetWithoutMentions().toLowerCase();
		// List<String> hashtagList = getHashtagsList();
		// for (String hashtag : hashtagList) {
		// String splitHashtag = hashtags.get(hashtag).getSegmentedHashtag();
		// tweetText = tweetText.replaceAll(hashtag, splitHashtag);
		// }
		// if (tweetText.isEmpty()) {
		// return tweetText;
		// } else {
		// Text text = new Text(tweetText);
		// StringBuilder cleanText = new StringBuilder();
		// for (String words : text.getTerms()) {
		// cleanText.append(words).append(" ");
		// }
		// return cleanText.toString().trim();

		// }

		throw new UnsupportedOperationException();

	}

	protected void removeNewLinesAndTabs() {
		text = text.replaceAll("[\t\n]", " ");
	}

	public String toJson() {
		return gson.toJson(this);
	}

}
