/**
 * Cristina Muntean Sep 23, 2014
 * twitter-core
 */
package it.cnr.isti.hpc.twitter.trends;

import it.cnr.isti.hpc.twitter.domain.TsvTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
import it.cnr.isti.hpc.twitter.util.Twokenize;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

public class CleanPunctuationTest {

	@Test
	public void test()  {
		WordFrequencyCLI cli = new WordFrequencyCLI();
		Twokenize tk = new Twokenize();
		String tweetText = "you  @tonto  #Daily  http://bit.ly/bJUhAJ http://bit.ly/bJUhAJ";
		System.out.println(cli.cleanTweetText("ananananana pippo ...pacca!"));
		System.out.println(cli.cleanTweetText(tweetText));
		
		System.out.println(tk.tokenizeRawTweetText(tweetText));
		System.out.println(tk.tokenize(tweetText));
		System.out.println(tk.tokenizeRawTweetText("ananananana pippo ...pacca!"));
		System.out.println(tk.tokenize("ananananana pippo ...pacca!"));
		//assertFalse(cli.cleanTweetText(tweetText).equals(["you",]));
		
		
		System.out.println(cli.getTimeinMilisFromFilename("/Users/cris/Documents/workspace/analytics/buckets/1402615685000.json.gz"));
		
		cli.cleanTweetTextNGramsDummy(" ananananana pippo ...pacca! you  @tonto  #Daily  http://bit.ly/bJUhAJ http://bit.ly/bJUhAJ");
	}

}
