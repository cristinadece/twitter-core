//package it.cnr.isti.hpc.twitter.domain;
//
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.IOException;
//import java.text.ParseException;
//
//import org.junit.Test;
//
//public class GetTweetDate {
//
//	@Test
//	public void test() {
//		JsonTweetReader reader = new JsonTweetReader(
//				"./src/test/resources/sample.json");
//
//		for (Tweet tweet : reader)
//			System.out.println(tweet.getText() + "\t" + "Date : "
//					+ tweet.getDateInMilliseconds());
//	}
//
//}
