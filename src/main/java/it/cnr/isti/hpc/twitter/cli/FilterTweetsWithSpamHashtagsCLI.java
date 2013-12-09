//package it.cnr.isti.hpc.twitter.cli;
//
//import it.cnr.isti.hpc.twitter.domain.Tweet;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class FilterTweetsWithSpamHashtagsCLI extends AbstractCommandLineInterface {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory.getLogger(FilterTweetsWithSpamHashtagsCLI.class);
//
//	/**
//	 * 
//	 */
//	public FilterTweetsWithSpamHashtagsCLI(String[] args) {
//		super(args);
//	}
//	
//	public static void main(String[] args){
//		FilterTweetsWithSpamHashtagsCLI cli = new FilterTweetsWithSpamHashtagsCLI(args);
//		cli.openOutput();
//		TsvTweetReader reader = new TsvTweetReader(cli.getInput());
//		Hashtags hashtags = Hashtags.getInstance();
//		for (Tweet t : reader){
//			boolean isSpam = false;
//			if (t.getHashtagsList().size() > 5){
//				logger.info("{} - contains more than 5 hashtags", t.getText());
//			}
//			for (String ht : t.getHashtagsList()){
//				if (hashtags.contains(ht)){
//					if (hashtags.get(ht).isSpam()){
//						isSpam = true;
//						logger.info("{} - {} is SPAM",ht, t.getText());
//						break;
//					}
//				}
//				
//			}
//			if (!isSpam){
//				cli.writeLineInOutput(t.toString());
//			}
//			
//		}
//		cli.closeOutput();
//	}
//}
