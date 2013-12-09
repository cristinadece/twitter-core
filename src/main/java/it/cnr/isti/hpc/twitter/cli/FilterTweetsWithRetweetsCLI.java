//package it.cnr.isti.hpc.twitter.cli;
//
//import it.cnr.isti.hpc.twitter.domain.Tweet;
//import it.cnr.isti.hpc.twitter.reader.TsvTweetReader;
//import it.cnr.isti.hpc.twitter.reader.TweetReader;
//import it.cnr.isti.hpc.twitter.util.InvalidTweetException;
//
//import java.io.IOException;
//
//public class FilterTweetsWithRetweetsCLI extends AbstractCommandLineInterface {
//
////	/**
////	 * input: a file with twits and hashtags and then output: a file with a list
////	 * of hashtags
////	 * 
////	 * the function returns a list of hashtags from twits that have been
////	 * retweeted
////	 * 
////	 * @param args
////	 */
////
////	public FilterTweetsWithRetweetsCLI(String[] args) {
////		super(args);
////	}
////
////	public static boolean isRT(String s) {
////		String[] items = s.trim().split(" ");
////		return items[0].toLowerCase().equals("rt");
////	}
////
////	public static void main(String[] args) throws IOException,
////			InvalidTweetException {
////		FilterTweetsWithRetweetsCLI cli = new FilterTweetsWithRetweetsCLI(args);
////		String input = cli.getInput();
////		TweetReader jr = null;
////		cli.openInputAndOutput();
////		jr = new TabFormatTweetReader(input);
////		TweetInterface t = null;
////		while ((t = jr.next()) != null) {
////			String line = t.getText();
////			// StringBuilder hashtaglist = new StringBuilder();
////			// List<String> hashtags = t.getHashtagsList();
////			if (isRT(line)) {
////				for (String s : t.getHashtagsList()) {
////					cli.writeLineInOutput(s);
////					System.out.println(s);
////				}
////			}
////
////		}
////		cli.closeInputAndOuput();
////	}
//}
