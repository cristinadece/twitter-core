package it.cnr.isti.hpc.twitter.domain;

import it.cnr.isti.hpc.io.reader.JsonRecordParser;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.IOException;

import org.junit.Test;

public class ItalianOrEnglish {

	@Test
	public void test() throws IOException, InvalidTweetException {
		RecordReader<JsonTweet> reader = new RecordReader<JsonTweet>(
				"./src/test/resources/diegoceccarelli.json.gz",
				new JsonRecordParser<JsonTweet>(JsonTweet.class));
		
		for (JsonTweet tweet : reader) {
			System.out.println(tweet.getText() +"\t"+tweet.getLanguage());
		}

	}

}
