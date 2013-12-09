//package it.cnr.isti.hpc.twitter.domain;
//
//import static org.junit.Assert.*;
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class testSegHt {
//
//		@Test
//		public void test() throws InvalidTweetException, IOException {
//			
////		   /* TweetReader jr = new JsonTweetReader(
////					"./src/test/resources/sample.json");
////			Tweet twit = null;
////			// System.out.println("has hashtags? " + twit.hasHashtags());
////			while ((twit = jr.next()) != null) {
////				Map<String, String> ht = twit.getSegmentedHashtag();
////				for (Map.Entry<String,String> h: ht.entrySet()){
////					System.out.print(h.getValue());					
////				}
////			    System.out.println();
////			}
////		    */
////			
////			Tweet t = new Tweet();
////			Map<String, String> dic = t.loadCamelHashtags();
////			int i=0;
////			for (Map.Entry<String,String> h: dic.entrySet()){
////				i++;
////				System.out.println(h.getKey() + "\t" + h.getValue());	
////				if (i==10) break;
////			}
////			
//	}
//
//}
