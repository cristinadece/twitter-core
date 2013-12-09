//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import org.junit.Test;
//
//public class TweetBeginsWithMention {
//
//	@Test
//	public void test() throws IOException, InvalidTweetException {
//		TsvTweet tweet = new TsvTweet(
//				"RT @504otos-You've got to love _Matisyahu_. I wrote and recorded this song with my friend Elijah just hours before he passed......		1312992484000");
//		assertFalse(tweet.beginsWithMentions());
//		tweet = new TsvTweet(
//				"@504otos-You've got to love _Matisyahu_. I wrote and recorded this song with my friend Elijah just hours before he passed......		1312992484000");
//		assertTrue(tweet.beginsWithMentions());
//	}
//
//}
