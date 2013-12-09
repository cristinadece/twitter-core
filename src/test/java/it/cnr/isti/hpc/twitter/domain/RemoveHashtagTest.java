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
//
//import java.io.IOException;
//
//import junit.framework.Assert;
//
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import org.junit.Test;
//
///**
// * RemoveHashtagTest.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 22/set/2011
// */
//public class RemoveHashtagTest {
//
//	@Test
//	public void test() throws IOException, InvalidTweetException {
//		TsvTweet tweet = new TsvTweet(
//				"its lunch time.. but im too lazy #asd to take #diego it somewhere...#kris	asd diego kris	1312992484000");
//		Assert.assertEquals("its lunch time.. but im too lazy to take it somewhere...".replaceAll(" ", ""),tweet.getTweetWithoutHashtags().replaceAll(" ",""));
//		
//	}
//
//}
