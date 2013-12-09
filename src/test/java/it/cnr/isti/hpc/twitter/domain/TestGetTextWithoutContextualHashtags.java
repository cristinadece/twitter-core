//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import org.junit.Test;
//
//public class TestGetTextWithoutContextualHashtags {
//
//	@Test
//	public void test1() throws InvalidTweetException {
//		TsvTweet tweet = new TsvTweet(
//				"test #diego test #kris	diego kris	1312992484000");
//		assertTrue(tweet.getTextWithoutContextualHashtags().contains("test"));
//		assertTrue(tweet.getTextWithoutContextualHashtags().contains("diego"));
//		assertFalse(tweet.getTextWithoutContextualHashtags().contains("#diego"));
//		assertFalse(tweet.getTextWithoutContextualHashtags().contains("kris"));
//		System.out.println(tweet.getTextWithoutContextualHashtags());
//
//		
//		tweet = new TsvTweet(
//				"test #diego test #kris #franco	diego kris franco	1312992484000");
//		assertTrue(tweet.getTextWithoutContextualHashtags().contains("test"));
//		System.out.println(tweet.getTextWithoutContextualHashtags());
//
//		tweet = new TsvTweet(
//				"test #diego test #kris. #franco	diego kris franco	1312992484000");
//		assertTrue(tweet.getTextWithoutContextualHashtags().contains("kris"));
//		System.out.println(tweet.getTextWithoutContextualHashtags());
//
//		tweet = new TsvTweet(
//				"#ijobers.com Private Project for YMS 3 by rob3139: Create main and inner page design to HTM... http://bit.ly/pRKhWc #onlinejob #Business	ijobers onlinejob business	1312992484000");
//		System.out.println(tweet.getTextWithoutContextualHashtags());
//		System.out.println(tweet.getContextHashtags());
//		System.out.println(tweet.getInLineHashtags());
//	}
//
//	/*
//	@Test
//	public void testTsvSample() throws InvalidTweetException, IOException {
//
//		TsvTweetReader jr = new TsvTweetReader(
//				"./src/test/resources/sample.tsv");
//
//		for (Tweet tweet : jr) {
//			if (tweet.hasHashtags()) {
//				System.out.println("Initial text : " + tweet.getText());
//				System.out.println("Clean text without Contextual : "
//						+ tweet.getTextWithoutContextualHashtags());
//			}
//
//		}
//	}
//	*/
//
//}
