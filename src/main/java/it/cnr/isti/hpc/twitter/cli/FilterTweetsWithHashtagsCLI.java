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
//package it.cnr.isti.hpc.twitter.cli;
//
//import it.cnr.isti.hpc.twitter.domain.JsonTweet;
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//import it.cnr.isti.hpc.twitter.util.Multiset;
//
//import java.io.IOException;
//
///**
// * FilterTwitsWithHashtagsCLI.java
// * Given a file containing twit in tab format, retrieves all the hashtag
// * with their frequencies
// * 
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 21/set/2011
// */
//public class FilterTweetsWithHashtagsCLI extends AbstractCommandLineInterface {
////
////	public FilterTweetsWithHashtagsCLI(String[] args) {
////		super(args);
////	}
////
////	public static void main(String[] args) {
////		FilterTweetsWithHashtagsCLI cli = new FilterTweetsWithHashtagsCLI(args);
////		String input = cli.getInput();
////		TweetReader jr = null;
////		try {
////			int count = 0;
////			cli.openInputAndOutput();
////			jr = new TabFormatTweetReader(input);
////			Tweet t = null;
////			while ((t = jr.next()) != null) {
////				if (t.hasHashtags()){
////					try {
////						cli.writeLineInOutput(t.dump());
////					} catch (InvalidTweetException e) {
////						System.err.println(e.toString());
////						continue;
////					}
////					count++;
////				}
////			}
////			cli.closeInputAndOuput();
////			System.out.println("added "+count+" twits");
////		} catch (IOException e) {
////			System.err.println(e.getMessage());
////			System.exit(-1);
////		}
////
////	}
//
//}
