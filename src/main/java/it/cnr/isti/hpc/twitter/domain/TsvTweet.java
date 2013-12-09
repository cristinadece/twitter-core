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

import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Tab separated value tweet. Its a tiny representation of a tweet containing
 * only text, hashtags, and creation time. Fields (text, hashtags, time in long
 * format) are encoded using tabs, e.g.,
 * 
 * <code>#mariomonti Non so sar� scarso \t #mariomonti \t 1234567890
 * 
 * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 09/gen/2012
 */
public class TsvTweet extends BasicTweet implements Tweet {
	
	private List<String> rawHashtags = Collections.emptyList();
	private transient List<String> hashtags = Collections.emptyList();
	private long date;

	/**
	 * Creates the tab separed representation of the tweet
	 * 
	 * @throws InvalidTweetException
	 */
	public TsvTweet(String tabSeparatedTweet) throws InvalidTweetException {
		/*
		 * fields[0] -> text fields[1] -> hashtags separated by spaces fields[2]
		 * -> time
		 */
		String[] fields = tabSeparatedTweet.split("\t");
		if (fields.length != 3) {
			throw new InvalidTweetException(
					"Invalid tsv line! line should contains text hashtags and time separated by tabs ("
							+ tabSeparatedTweet + " )");
		}
		text = fields[0];
		if (!isLegal()) {
			throw new InvalidTweetException("invalid tweet");
		}
		
		parseHashtags(fields[1]);
		
		try {
			date = Long.parseLong(fields[2]);
		} catch (NumberFormatException e) {
			throw new InvalidTweetException(
					"Invalid tsv line! line should contains a valid creation time in long format ("
							+ tabSeparatedTweet + " )");
		}
		removeNewLinesAndTabs();

	}
	
	public TsvTweet(String text, String hashtagsLine, long date){
		this.text = text;
		parseHashtags(hashtagsLine);
		this.date = date;
		removeNewLinesAndTabs();
	}
	
	public TsvTweet(String text, String hashtagsLine){
		this.text = text;
		parseHashtags(hashtagsLine);
		this.date = 0;
		removeNewLinesAndTabs();
	}
	
	private void parseHashtags(String hashtagsLine){
		if (!hashtagsLine.isEmpty()) {
			Scanner s = new Scanner(hashtagsLine);
			this.hashtags = new ArrayList<String>(10);
			rawHashtags = new ArrayList<String>(10);

			while (s.hasNext()) {
				String ht = s.next();
				this.hashtags.add(ht.toLowerCase());
				rawHashtags.add(ht);
			}
		}
	}

	@Override
	public long getDateInMilliseconds() {
		return date;
	}

	@Override
	public List<String> getHashtagsList() {
		if (hashtags == null){
			hashtags = new ArrayList<String>();
			for (String ht : getRawHashtagsList()){
				hashtags.add(ht.toLowerCase());
			}
		}
		return hashtags;
	}

	@Override
	public List<String> getRawHashtagsList() {
		return rawHashtags;
	}

	

	public String getUsername() {
		throw new UnsupportedOperationException();
	}



}
