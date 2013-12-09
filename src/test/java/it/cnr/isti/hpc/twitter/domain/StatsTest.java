///**
// *  Copyright 2011 Diego Ceccarelli
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// * 
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//import it.cnr.isti.hpc.twitter.domain.prediction.ShortcutsPrediction;
//import it.cnr.isti.hpc.twitter.domain.prediction.Prediction;
//import it.cnr.isti.hpc.twitter.domain.stats.Stats;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TsvReaderTest;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//
//import junit.framework.Assert;
//
//import org.junit.Ignore;
//import org.junit.Test;
//
///**
// * StatsTest.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 20/set/2011
// */
//public class StatsTest {
//
//	
//	@Test
//	public void test() throws IOException, InvalidTweetException {
//		
//		Stats precision3 = new Stats(3);
//		Stats precision10 = new Stats(10);
//		String st = "";
//		TsvTweetReader reader = new TsvTweetReader("./src/test/resources/sample.tsv");
//		
//		for (Tweet tweet : reader){
//			
//			Prediction p = ShortcutsPrediction.getInstance(tweet);
//			if (p.getRecall() > 0)
//				System.out.println(p);
//		
//			precision3.evaluatePrediction(tweet, p);
//			precision10.evaluatePrediction(tweet, p);
//		}
////		Assert.assertEquals(464, precision10.getNumberOfTwit());
////		Assert.assertEquals(464, precision3.getNumberOfTwit());
////		Assert.assertEquals(58, precision10.getNumberOfTwitsWithHashtag());
////		Assert.assertEquals(58, precision3.getNumberOfTwitsWithHashtag());
////		Assert.assertEquals(72, precision10.getTotalNumberOfHashtags());
////		Assert.assertEquals(72, precision3.getTotalNumberOfHashtags());
////		Assert.assertEquals(33, precision10.getTotalNumberOfHashtagsCorrectlySuggested());
////		Assert.assertEquals(26,precision10.getNumberOfTwitsWithAtLeastOneHashtagPredicted());
////		Assert.assertTrue(Math.abs(0.42672417 - precision10.getRecall()) < 0.00001);
////		Assert.assertTrue(precision10.getRecall() >= precision3.getRecall());
////		System.out.println(precision10);
////		System.out.println(precision3);
//		
//	
//	}
//}
