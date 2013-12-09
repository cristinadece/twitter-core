//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import org.junit.Test;
//
//public class InlineContextHashtags {
//	
//	
//	@Test
//	public void testHashtags() throws InvalidTweetException {
//		TsvTweet tweet = new TsvTweet(
//				"test #diego test #kris http://t.co/D9lqM1S #wrong	diego kris wrong	1312992484000");
//		assertTrue(tweet.getContextHashtags().contains("kris"));
//		assertTrue(tweet.getInLineHashtags().contains("diego"));
//		
//			
//		
//		tweet = new TsvTweet(
//				"test #diego test #kris #franco	diego kris franco	1312992484000");
//		assertTrue(tweet.getContextHashtags().contains("franco"));
//		
//		
//		tweet = new TsvTweet(
//				"test #diego test #kris. #franco www.google.ro	diego kris franco	1312992484000");
//		
//		assertFalse(tweet.getContextHashtags().contains("kris"));
//		assertTrue(tweet.getInLineHashtags().contains("kris"));
//	
//		tweet = new TsvTweet(
//				"#ijobers.com Private Project for YMS 3 by rob3139: Create main and inner page design to HTM... http://bit.ly/pRKhWc #onlinejob #Business	ijobers onlinejob business	1312992484000");
//
//		assertTrue(tweet.getInLineHashtags().contains("ijobers"));
//		assertFalse(tweet.getInLineHashtags().contains("business"));
//		
//		
//		tweet = new TsvTweet(
//				"Que poco falta para el domingo!!! Ya les dije que #NoVotoaCFK ?	NoVotoaCFK 	1312992472000");
//
//		assertTrue(tweet.getInLineHashtags().contains("novotoacfk"));
//		assertTrue(tweet.getContextHashtags().isEmpty());
//		assertFalse(tweet.getInLineHashtags().contains("business"));
//	
//
//	}
//	
//
//	@Test
//	public void testContextInlineHashtags() throws InvalidTweetException {
//		TsvTweet tweet = new TsvTweet(
//				"test #diego test. #ana.;#kris #karma	diego ana kris karma	1312992484000");
//		//assertTrue(tweet.getContextHashtags().contains("ana"));
//		System.out.println("Contextual : " + tweet.getContextHashtags());
//		//assertTrue(tweet.getInLineHashtags().contains("ana"));
//		System.out.println("Inline : " + tweet.getInLineHashtags());		
//	}
//
//	
//	
//	
//	/*
//	@Test
//	public void testHashtagsInJson() throws InvalidTweetException, IOException {
//
//		TsvTweetReader jr = new TsvTweetReader(
//				"./src/test/resources/sample.tsv");
//
//		for (Tweet tweet : jr) {
//			if (tweet.hasHashtags()) {
//
//				System.out.println("Hashtag list: " + tweet.getHashtagsList());
//				System.out.println("Context hashtags: "
//						+ tweet.getContextHashtags());
//				System.out.println("Inline hashtags: "
//						+ tweet.getInLineHashtags());
//
//			}
//		}
//	}
//	*/
//	
//	/*
//	@Test
//	public void testHahstagList() throws InvalidTweetException, IOException {
//
//		TsvTweet tweet = new TsvTweet(
//				"#messi I'm in need of a man for sex http://t.co/ug985eZb	messi	1312992484000");
//	
//		System.out.println("List is " + tweet.getHashtagsList());
//	}
//	*/
//
//	
//
//}
