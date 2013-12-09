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
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.IOException;
//
//import junit.framework.Assert;
//
//import org.junit.Ignore;
//import org.junit.Test;
//
///**
// * TermsTest.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 22/set/2011
// */
//public class TermsTest {
//	@Ignore
//	@Test
//	public void test() throws IOException, InvalidTweetException {
//		Terms t = new Terms();
//		t.addTerms("diego diego #pippo pippo diego. pluto ......pluto");
//		System.out.println(t.toString());
//		Assert.assertEquals(3, t.getFrequency("diego"));
//		Assert.assertEquals(2, t.getFrequency("pippo"));
//		Assert.assertEquals(2, t.getFrequency("pluto"));
//		
////		TweetInterface twit = null;
////		TweetReader jr = new JsonTweetReader("./src/test/resources/sample.json");
////		twit = jr.next();
////		while (!twit.hasHashtags())
////			twit = jr.next();
////		t = new Terms();
////		t.addTwit(twit);
////		System.out.println(twit.getText());
////		System.out.println(t);
//	}
//	
////	@Test
////	public void testTraining() throws IOException, InvalidTweetException {
////		Terms t = Terms.loadTrainingTerms();
////		System.out.println("life probability :" + t.getProbability("life"));
////	}
//
//}
