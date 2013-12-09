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
package it.cnr.isti.hpc.twitter.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.JsonSyntaxException;

/**
 * TwitDumpTest.java
 * 
 * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 21/set/2011
 */
public class TweetDumpTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(TweetDumpTest.class);

	@Test
	public void testDump() throws InvalidTweetException {
//		InputStream is = TestParseJson.class
//				.getResourceAsStream("/sample.json");
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		String st = "";
//		try {
//			while ((st = br.readLine()) != null) {
//				StringReader myStringReader = new StringReader(st);
//				try {
//					Tweet t = Tweet.parseTwitFromJson(myStringReader);
//
//					System.out.println(t.dump());
//				} catch (InvalidTweetException e2) {
//					continue;
//				}
//			}
//		} catch (IOException e) {
//			// System.err.println("Error reading from Reader :" +
//			// e.getMessage());
//		} catch (JsonSyntaxException e1) {
//			logger.error("malformed line: {} ({})", st, e1.toString());
//		}

	}
	
	
	@Test
	public void testLoad() throws InvalidTweetException {
//		InputStream is = TestParseJson.class
//				.getResourceAsStream("/sample.json");
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		String st = "";
//		int count = 0;
//		try {
//			while ((st = br.readLine()) != null) {
//				StringReader myStringReader = new StringReader(st);
//				try {
//					Tweet t = Tweet.parseTwitFromJson(myStringReader);
//
//					System.out.println(t.dump());
//					TweetInterface tLoaded = Tweet.load(t.dump());
//					Assert.assertEquals(t.getText(), tLoaded.getText());
//					List<String> originalHt = t.getHashtagsList();
//					List<String> loadedHt = tLoaded.getHashtagsList();
// 					for (int i  = 0; i < originalHt.size(); i++)
//					Assert.assertEquals(originalHt.get(i), tLoaded.getHashtagsList().get(i));
//				} catch (InvalidTweetException e2) {
//					continue;
//				}
//				count++;
//			}
//		} catch (IOException e) {
//			// System.err.println("Error reading from Reader :" +
//			// e.getMessage());
//		} catch (JsonSyntaxException e1) {
//			logger.error("malformed line: {} ({})", st, e1.toString());
//		}
//		System.out.println(count);

	}
	
}
