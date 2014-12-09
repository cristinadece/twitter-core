/**
 * Cristina Muntean Sep 10, 2014
 * twitter-core
 */
package it.cnr.isti.hpc.twitter.trends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.JsonObject;

public class Keyword {

	private String trend;
	private String time;
	// this is an item which holds the frequency of the keyword per bucket 
	// (identified with timeInMillis)
	private transient LinkedHashMap<String, Integer> bucketFrequency;
	private List<String> ids;
	private List<String> users;
	//private List<String> tweets;
	private List<JsonObject> tweetsJ;
	
	public List<JsonObject> getTweetsJ() {
		return tweetsJ;
	}

	public void setTweetsJ(List<JsonObject> tweetsJ) {
		this.tweetsJ = tweetsJ;
	}
	
	public void addTweetsJ(JsonObject tweet){
		if (this.tweetsJ.size()<=11){
			if (!this.tweetsJ.contains(tweet)){
				this.tweetsJ.add(tweet);
			}
		}
	}
	private List<String> entities;
	private double zscore;
	private double frequency;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getEntities() {
		return entities;
	}

	public void setEntities(List<String> entities) {
		this.entities = entities;
	}
	
	public void addEntity(String entity){
		
		if (!this.entities.contains(entity)){
			this.entities.add(entity);
		}
	}
	
//	public List<String> getFirst50tweets() {
//		return tweets;
//	}
//
//	public void setFirst50tweets(List<String> first50tweets) {
//		this.tweets = first50tweets;
//	}
//	
//	public void addFirst50tweets(String tweet){
//		if (this.tweets.size()<=12){
//			if (!this.tweets.contains(tweet)){
//				this.tweets.add(tweet);
//			}
//		}
//	}


	public double getZscore() {
		return zscore;
	}

	public void setZscore(double zscore) {
		this.zscore = zscore;
	}

	public double getBasefreq() {
		return frequency;
	}

	public void setBasefreq(double basefreq) {
		this.frequency = basefreq;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
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
		return ids;
	}

	public void setTweetIds(List<String> tweetIds) {
		this.ids = tweetIds;
	}
	
	public void addTweetId(String tweetId){
		if (!this.ids.contains(tweetId)){
			this.ids.add(tweetId);
		}
	}

	public List<String> getUserIds() {
		return users;
	}

	public void setUserIds(List<String> userIds) {
		this.users = userIds;
	}
	
	public void addUserId(String userId){
		if (!this.users.contains(userId)){
			this.users.add(userId);
		}
	}

	public Keyword() {
		bucketFrequency = new LinkedHashMap<String, Integer>();
		ids = new ArrayList<String>();
		users = new ArrayList<String>();
		//tweets = new ArrayList<String>();
		tweetsJ = new ArrayList<JsonObject>();
	}
	
	public Keyword(String word) {
		trend = word;
		//init
		bucketFrequency = new LinkedHashMap<String, Integer>();
		ids = new ArrayList<String>();
		users = new ArrayList<String>();
		//tweets = new ArrayList<String>();
		tweetsJ = new ArrayList<JsonObject>();
	}
	
//	public Keyword(String word, String interval, String tweetId, String userID, String tweet) {
//		
//		//init
//		bucketFrequency = new LinkedHashMap<String, Integer>();
//		ids = new ArrayList<String>();
//		users = new ArrayList<String>();
//		//tweets = new ArrayList<String>();
//		tweetsJ = new ArrayList<JsonObject>();
//		
//		trend = word;
//		bucketFrequency.put(interval, 1);
//		ids.add(tweetId);
//		users.add(userID);
//		tweets.add(tweet);
//	}
	
	public Keyword(String word, String interval, String tweetId, String userID, JsonObject tweet) {
		
		//init
		bucketFrequency = new LinkedHashMap<String, Integer>();
		ids = new ArrayList<String>();
		users = new ArrayList<String>();
		//tweets = new ArrayList<String>();
		tweetsJ = new ArrayList<JsonObject>();
		
		trend = word;
		bucketFrequency.put(interval, 1);
		ids.add(tweetId);
		users.add(userID);
		tweetsJ.add(tweet);
	}

	public static void main(String[] args) {
		
	}

}
