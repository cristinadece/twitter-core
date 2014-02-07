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

import java.util.List;

/**
 * TweetInterface.java
 * 
 * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 08/gen/2012
 */
public interface Tweet {

	/**
	 * Returns the text of the tweet
	 * 
	 * @return the text of the tweet
	 */
	public abstract String getText();

	public abstract boolean hasGeo();

	public abstract boolean hasPlace();

	/**
	 * Returns the text of the tweet without mentions or contextual hashtags &
	 * the inline hashtags are segmented
	 * 
	 * @return the text of the tweet
	 */
	// public abstract String getTextWithoutContextualHashtags();

	/**
	 * Returns the text of a string without mentions
	 * 
	 * @return the text without mentions
	 */

	// public abstract String getTextWithoutOneContextualHashtag(String
	// contextualHt);

	/**
	 * Returns the text of a tweet without one hashtag passed as paramether
	 * 
	 * @return the text without mentions
	 */

	// public abstract String getTextWithoutMentions(String s);

	/**
	 * Returns the nickname of the author of the tweet
	 * 
	 * @return the username of the author
	 */
	public abstract String getUsername();

	/**
	 * Returns a string representing the language of a tweet.
	 * 
	 * @return a string representing the language of a tweet;
	 */
	public abstract String getLanguage();

	/**
	 * Checks if the language of the tweet is English.
	 * 
	 * @return true if the tweet is in English, false otherwise
	 */
	public abstract boolean isEnglish();

	/**
	 * Checks if the language of the tweet is Italian.
	 * 
	 * @return true if the tweet is in English, false otherwise
	 */
	public abstract boolean isItalian();

	/**
	 * Returns a list containing the inline hashtags (lowercased)
	 * 
	 * @return a list containing the inline hashtags
	 */
	public abstract List<String> getInLineHashtags();

	/**
	 * Returns a list containing the context hashtags (lowercased)
	 * 
	 * @return a list containing the context hashtags
	 */
	public abstract List<String> getContextHashtags();

	/**
	 * Returns a list containing the segmented hashtags (lowercased)
	 * 
	 * @return a list containing the segmented hashtags
	 */
	// public abstract String getSegmentedHashtags();

	/**
	 * Returns the date of creation of the tweets in milliseconds
	 * 
	 * @return the date of creation of a tweet in milliseconds
	 */
	public abstract long getDateInMilliseconds();

	/**
	 * Returns the list of the hashtags apparing in twit. Hashtags are
	 * lowercased.
	 * 
	 * @return the hashtags apparing in twit. Hashtags are lowercased.
	 */
	public abstract List<String> getHashtagsList();

	/**
	 * Returns the list of the hastags contained within the twit, without
	 * lowercasing them
	 * 
	 * @return the list of the hasttags containined within the twit.
	 */
	public abstract List<String> getRawHashtagsList();

	/**
	 * Returns the list of terms contained in a tweet (including hashtags). The
	 * tweet is tokenized using Lucene (@see
	 * org.apache.lucene.analysis.Tokenizer)
	 * 
	 * @return the list of terms appearing in a tweet
	 */
	public abstract List<String> getTerms();

	/**
	 * Tests if the tweet contains hashtags.
	 * 
	 * @return true if the tweet contains at least one hashtag, otherwise false
	 */
	public abstract boolean hasHashtags();

	/**
	 * Filters out all characters that are not alphanumeric. and replace
	 * hashtags with their segmentation
	 * 
	 * @return the clean text (only spaces and alphanumeric chars)
	 */
	public abstract String getCleanTweet();

	/**
	 * Returns the <i>lowercased_<i> text of the tweet without the hashtags
	 * 
	 * @return the <i>lowercased_<i> text of the tweet without the hashtags
	 */
	public abstract String getTweetWithoutHashtags();

	/**
	 * Returns the <i>lowercased_<i> text of the tweet without the mentions
	 * 
	 * @return the <i>lowercased_<i> text of the tweet without the mentions
	 */
	public abstract String getTweetWithoutMentions();

	/**
	 * Tests if the tweet is a retweet, i.e. starts with RT:.
	 * 
	 * @return true if the tweet contains at least one hashtag, otherwise false
	 */
	public abstract boolean isRetweet();

	/**
	 * Tests if the tweet contains only URLs.
	 * 
	 * @return true if the tweet contains only URLs, otherwise false
	 */
	public abstract boolean isOnlyURL();

	/**
	 * Tests if the tweet contains only URLs and hashtags.
	 * 
	 * @return true if the tweet contains only URLs and hashtags, otherwise
	 *         false
	 */
	public abstract boolean isOnlyURLAndHashtags();

	/**
	 * Tests if the tweet contains URLs.
	 * 
	 * @return true if the tweet contains URLs, otherwise false
	 */
	public abstract boolean hasUrl();

	/**
	 * verifies if text of a tweet is <i> legal </i> i.e., if it contains at
	 * least an alphabetic char
	 * 
	 * @return true if is a regular twit
	 */
	public abstract boolean isLegal();

	/**
	 * Tests if the tweet contains mentions.
	 * 
	 * @return true if the tweet contains URLs, otherwise false
	 */
	public abstract boolean hasMentions();

	/**
	 * Tests if the tweet starts with a mention.
	 * 
	 * @return true if the tweet starts with a mention, otherwise false
	 */
	public abstract boolean beginsWithMentions();

	/**
	 * return a list of lines to index. For each hashtag in a twit one line
	 * containing:
	 * <ul>
	 * <li>the hashtag</li>
	 * <li>the text</li>
	 * <li>the related hashtags</li>
	 * <li>the text with the hashtags replaced by the terms occurring in them</li>
	 * <li>the related hashtags replaced by the terms occurring in the them</li>
	 * </ul>
	 * 
	 * @return the fields separated by a tab
	 * @throws InvalidTweetException
	 * @throws IOException
	 */
	// public abstract String getIndexLine();

	/**
	 * Given an hashtag in the tweet, returns a string containing other hashtags
	 * contained in the twit.
	 * 
	 * @param hashtag
	 *            - the hashtag selected
	 * @return the other hashtags
	 */
	public abstract String getRelatedHashtags(String hashtag);

	public abstract String toJson();

}