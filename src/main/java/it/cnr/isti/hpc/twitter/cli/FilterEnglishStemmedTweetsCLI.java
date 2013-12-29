/**
 *  Copyright 2011 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.io.reader.JsonRecordParser;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;

/**
 * Given a collection of json tweets filters only english tweets, produces file
 * where each line contains the stemmed text of the tweet.
 * 
 * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 21/set/2011
 */
public class FilterEnglishStemmedTweetsCLI extends AbstractCommandLineInterface {

	public FilterEnglishStemmedTweetsCLI(String[] args) {
		super(args);
	}

	public static void main(String[] args) {
		FilterEnglishStemmedTweetsCLI cli = new FilterEnglishStemmedTweetsCLI(
				args);
		RecordReader<JsonTweet> reader = new RecordReader<JsonTweet>(
				cli.getInput(),
				new JsonRecordParser<JsonTweet>(JsonTweet.class));
		ProgressLogger pl = new ProgressLogger("readed {} tweets", 100000);
		cli.openOutput();
		for (JsonTweet tweet : reader) {
			pl.up();
			if (tweet.isEnglish()) {
				cli.writeLineInOutput(tweet.getStemmedText());
			}
		}
		cli.closeOutput();

	}

}
