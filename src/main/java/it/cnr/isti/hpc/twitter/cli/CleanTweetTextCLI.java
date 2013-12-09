//package it.cnr.isti.hpc.twitter.cli;
//
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.domain.Tweet;
//import it.cnr.isti.hpc.twitter.reader.JsonTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//
///*FIXME reimplement */
//public class CleanTweetTextCLI extends AbstractCommandLineInterface {
//
////	/**
////	 * Reads the Tweets and cleans the content from @ mentions and links, URL
////	 * 
////	 * @param args
////	 */
////
////	public CleanTweetTextCLI(String[] args) {
////		super(args);
////	}
////	
////	public static boolean isURL(String s){
////		
////		try {
////			URL url = new URL(s);
////		} catch (MalformedURLException e) {
////			return false;
////		}
////		return true;
////	}
////	
////	public static boolean isMention(String s){
////		if (s.length()==0){
////			return false;
////		}
////		return s.charAt(0)=='@';
////	}
////	
////	public static boolean isHashtag(String s){
////		if (s.length()==0){
////			return false;
////		}
////		return s.charAt(0)=='#';
////	}
////		
////	public static boolean isRT(String s){
////		return s.toLowerCase().equals("rt");
////	}
////	
////	public static String removeMentionsUrlFromTwit(String line){
////			StringBuilder cleanTweet = new StringBuilder();
////			String[] items = line.split(" ");
////			for (String s : items) {
////				if (! ( isMention(s) || isURL(s) || isRT(s) ) ){
////					cleanTweet.append(s).append(" ");
////				} 
////			}
////			//System.out.println("*" + line + "\n" + cleanTweet.toString());
////			return cleanTweet.toString();
////	}
////	
////	public static boolean onlyHashtags(String line){
////		boolean isText=true;
////		String[] items = line.split(" ");
////		for (String s:items){
////			if (!isHashtag(s)){
////				isText=false;
////			}
////		}
////		return isText;
////	}
////
////
////	public static void main(String[] args) {
////		CleanTweetTextCLI cli = new CleanTweetTextCLI(args);
////		String input = cli.getInput();
////		TabFormatTweetReader jr = new TabFormatTweetReader(input);
////		cli.openOutput();
////		
////	
////		for (TweetInterface t : jr){
////			
////			if (onlyHashtags(line)){
////				cli.writeLineInOutput(line);
////				//System.out.println("Twit contains only hashtags\t" +line);
////			}
////			if ((jr.getTweetReaded() % 1000) == 0){
////				System.out.print(jr.getTweetReaded()+" ");
////			}
////			
////		}
////		System.out.println("\n");
////		
////	}
//
//}
