package it.cnr.isti.hpc.twitter.domain;

import static org.junit.Assert.*;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.IOException;

import org.junit.Test;

public class IsOnlyHashtagsAndUrl {

		@Test
		public void test() throws IOException, InvalidTweetException {
			TsvTweet tweet = new TsvTweet(
					"you    #Daily  http://bit.ly/bJUhAJ http://bit.ly/bJUhAJ	daily	1312992484000");
			assertFalse(tweet.isOnlyURLAndHashtags());
			tweet = new TsvTweet(
					"    #Daily  http://bit.ly/bJUhAJ http://bit.ly/bJUhAJ	daily	1312992484000");
			assertTrue(tweet.isOnlyURLAndHashtags());
		}
	

}
