//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.IOException;
//
//import org.junit.Ignore;
//import org.junit.Test;
//
//public class HasUrlOrMentions {
//
//	@Ignore
//	@Test
//	public void testUrlAndMentions() throws IOException, InvalidTweetException {
//
//		TsvTweet tweet = new TsvTweet(
//				"RT @504otos-You've got to love _Matisyahu_. I wrote and recorded this song with my friend Elijah just hours before he passed......		1312992484000");
//		assertTrue(tweet.hasMentions());
//		assertFalse(tweet.hasHashtags());
//		assertTrue(tweet.isRetweet());
//		tweet = new TsvTweet(
//				"The Net-Neutrality #Daily is out! http://bit.ly/bJUhAJ ▸ Top stories today via @lux113 @wllegal	Daily	1312992478000");
//		assertTrue(tweet.hasUrl());
//		assertTrue(tweet.hasMentions());
//		assertTrue(tweet.hasHashtags());
//		tweet = new TsvTweet(
//				"    #Daily  http://bit.ly/bJUhAJ http://bit.ly/bJUhAJ	Daily	1312992478000");
//		assertTrue(tweet.isOnlyURLAndHashtags());
//	}
//
//}
