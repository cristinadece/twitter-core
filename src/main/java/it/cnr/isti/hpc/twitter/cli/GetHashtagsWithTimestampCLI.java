package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;


/**
 * This class parses the Json and returns a list of hashtags, each with its
 * corresponding timestamp lists (hashtags and a list of the time it occured)
 */
public class GetHashtagsWithTimestampCLI extends AbstractCommandLineInterface {
//
//	// the life of a hashtag
//	public static Long lifeSpan(String hashtag,
//			HashMap<String, List<Date>> htlist) {
//		Integer n = 0;
//		List<Date> timestamps = htlist.get(hashtag);
//		System.out.println(timestamps.toString());
//		if (timestamps.size() > 1) {
//			Long time = timestamps.get(timestamps.size() - 1).getTime()
//					- timestamps.get(0).getTime();
//			return time;
//		} else {
//			return n.longValue();
//		}
//	}
//
//	public static void main(String[] args) throws IOException, ParseException {
//		CleanTweetTextCLI cli = new CleanTweetTextCLI(args);
//		String input = cli.getInput();
//		TweetReader jr = null;
//		cli.openInputAndOutput();
//		jr = new JsonTweetReader(input);
//		TweetInterface t = null;
//		HashMap<String, List<Date>> htlist = new HashMap<String, List<Date>>();
//		System.out.println("reading twits");
//		while ((t = jr.next()) != null) {
//			if ( jr.getTweetReaded() % 1000 == 0) System.out.print(jr.getTweetReaded()+" ");
//			// Date data = Date.class.cast(t.getCreated_at());
//			DateFormat formatter = new SimpleDateFormat(
//					"EEE MMM dd HH:mm:ss yyyy");
//			Date data = formatter
//					.parse(t.getCreated_at().replace(" +0000", ""));
//			for (String s : t.getHashtagsList()) {
//				if (htlist.containsKey(s)) {
//					List<Date> timestamp =  htlist.get(s);
//					timestamp.add(data);
//					//System.out.println("adding: " + s + "\t" + timestamp);
//					htlist.put(s, timestamp);
//				} else {
//					List<Date> timestamp = new LinkedList<Date>();
//					timestamp.add(data);
//					//System.out.println("adding: " + s + "\t" + timestamp);
//					htlist.put(s, timestamp);
//				}
//
//			}
//		}
//		System.out.println("\n");
//		System.out.println("writing hashtags times");
//		for (Map.Entry<String, List<Date>> e : htlist.entrySet()){
//			if (e.getValue().size() > 1){
//			cli.writeInOutput(e.getKey());
//			cli.writeInOutput("\t");
//			List<Date> occurrenceTimes = e.getValue();
//			long length = occurrenceTimes.get(occurrenceTimes.size()-1).getTime()-occurrenceTimes.get(0).getTime();
//			cli.writeInOutput(+length+"\t"+((int)(length/(3600000)))); // (int)(length/(3600000)  == length in hours
////			for (Date d : e.getValue()){
////				cli.writeInOutput(d.getTime()+" ");
////			}
//			cli.writeInOutput("\n");
//			}
//			else cli.writeLineInOutput(e.getKey()+"\t"+0+"\t"+0);
//		}
//		
//		cli.closeInputAndOuput();
//
////		for (String s : htlist.keySet()) {
////			System.out.print(s);
////			System.out.println(lifeSpan(s.toString(), htlist));
////		}
//
//	}

}
