//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//
//import it.cnr.isti.hpc.twitter.domain.hashtag.HashtagSegmenter;
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.util.Map;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.gson.JsonSyntaxException;
//
//public class TestHashtagSegmentation {
//	
//	transient private static final Logger logger = LoggerFactory
//			.getLogger(TestHashtagSegmentation.class);
//
//	
//	@Ignore
//	@Test
//	public void test() throws InvalidTweetException, IOException {
//		
//	    TsvTweetReader jr = new TsvTweetReader(
//				"./src/test/resources/sample.tsv");
//
//	    for (Tweet tweet : jr){
//	    	if (tweet.hasHashtags()){
//	    		
//	    		System.out.println("Old Tweet: "+tweet.getText());
//	    		System.out.println("Clean Tweet: "+ tweet.getCleanTweet());
//	    		
//	    		System.out.println("Hashatgs: "+tweet.getHashtagsList().toString());
//	    		System.out.println("Segm hashtags: "+ tweet.getSegmentedHashtags());
//			}
//		}
//	    
//	    
//		
//	}
//
//}
