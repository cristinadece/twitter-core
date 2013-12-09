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
//import it.cnr.isti.hpc.twitter.cli.Json2TabFormatCLI;
//import it.cnr.isti.hpc.twitter.domain.hashtag.Hashtag;
//import it.cnr.isti.hpc.twitter.domain.hashtag.Hashtags;
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import junit.framework.Assert;
//
//import org.apache.solr.common.util.Hash;
//import org.junit.Ignore;
//import org.junit.Test;
//
///**
// * HashtagsTest.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 22/set/2011
// */
//public class HashtagsTest {
//	
//	
//
//	
//	/*
//	 *  1 teamfollowback  274078
//     *  2 np      238054
//     *  3 oomf    214025
//     *  4 nowplaying      178580
//     *  5 ff      177882
//     *  6 20factsaboutme  140178
//     *  7 jobs    85715
//     *  8 nf      85127
//     *  9 xfactor 76789
//     * 10 tfb     66709
//	 *
//	 */
//	@Test
//	public void testTop10Hashtags() throws IOException{
//		List<Hashtag> top10ht = Hashtags.getInstance().getTopTrainingHashtags(10);
//	//	List<String> top = Arrays.asList(new String[] { "teamfollowback","np","oomf","nowplaying","ff","20factsaboutme","jobs","nf","xfactor","tfb" });
//		for (Hashtag ht : top10ht){
//			//assertTrue(top10ht.contains(ht));
//			System.out.println(ht);
//		}
////		assertEquals(10, top10ht.size());
////		top10ht = Hashtags.getTopKHashtagInTrainingSet(64030,5);
////		top = Arrays.asList(new String[] { "job","peopleschoice","winning","fb","instantfollowback" });
////		for (String ht : top){
////			assertTrue(top10ht.contains(ht));
////		}
////		assertEquals(5, top10ht.size());
//	}
//	
//	@Test
//	public void testSkipHashtags() throws IOException{
////		Hashtags hashtags = Hashtags.getTrainingHashtags(15731);
////		assertTrue(hashtags.contains("2behonest"));
////		assertFalse(hashtags.contains("np"));
////		assertFalse(hashtags.contains("twitter"));
////		assertTrue(hashtags.contains("subtweet"));
////		assertTrue(hashtags.contains("02newcastle"));
////		assertTrue(hashtags.contains("008z"));
//	}
//	
//	
//	
//	
//	
//
//	
//	
//
//}
