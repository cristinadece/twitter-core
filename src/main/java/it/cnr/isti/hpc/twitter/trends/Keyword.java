/**
 * Cristina Muntean Sep 10, 2014
 * twitter-core
 */
package it.cnr.isti.hpc.twitter.trends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Keyword {

	private String name;
	// this is an item which holds the frequency of the keyword per bucket 
	// (identified with timeInMillis)
	private LinkedHashMap<String, Integer> bucketFrequency;
	private List<String> tweetIds;
	private List<String> userIds;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public LinkedHashMap<String, Integer> getBucketFrequency() {
		return bucketFrequency;
	}

	public void setBucketFrequency(LinkedHashMap<String, Integer> bucketFrequency) {
		this.bucketFrequency = bucketFrequency;
	}
	
	public void addBucketFrequency(String timestamp, int freq){
		
		if (this.bucketFrequency.containsKey(timestamp)){
			int i = bucketFrequency.get(timestamp) + 1;
			bucketFrequency.put(timestamp, i);
		}
		else{
			this.bucketFrequency.put(timestamp, freq);
		}
	}

	public List<String> getTweetIds() {
		return tweetIds;
	}

	public void setTweetIds(List<String> tweetIds) {
		this.tweetIds = tweetIds;
	}
	
	public void addTweetId(String tweetId){
		if (!this.tweetIds.contains(tweetId)){
			this.tweetIds.add(tweetId);
		}
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
	public void addUserId(String userId){
		if (!this.userIds.contains(userId)){
			this.userIds.add(userId);
		}
	}

	public Keyword() {
		bucketFrequency = new LinkedHashMap<String, Integer>();
		tweetIds = new ArrayList<String>();
		userIds = new ArrayList<String>();
	}
	
	public Keyword(String word) {
		name = word;
		//init
		bucketFrequency = new LinkedHashMap<String, Integer>();
		tweetIds = new ArrayList<String>();
		userIds = new ArrayList<String>();
	}
	
	public Keyword(String word, String interval, String tweetId, String userID) {
		
		//init
		bucketFrequency = new LinkedHashMap<String, Integer>();
		tweetIds = new ArrayList<String>();
		userIds = new ArrayList<String>();
		
		name = word;
		bucketFrequency.put(interval, 1);
		tweetIds.add(tweetId);
		userIds.add(userID);
	}

	public static void main(String[] args) {
		
	}

}
