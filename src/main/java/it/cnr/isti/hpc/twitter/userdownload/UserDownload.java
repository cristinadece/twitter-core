package it.cnr.isti.hpc.twitter.userdownload;

import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.util.TwitterProperties;

import java.io.BufferedWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class UserDownload {

	private static final Logger logger = LoggerFactory.getLogger(UserDownload.class);
	
	public void dumpUser(String user, String fileOut) {
		try {
 
			Twitter twitter = getAuthTwitter();
			
            //printa su file il dump degli status
            printOnFileHomeTimeline(twitter, user, fileOut);
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed: " + e.getMessage());
            System.exit(-1);
        } 
	}
	
	//ritorna un oggetto Twitter autenticato con tramite il file property
	private static Twitter getAuthTwitter(){
		
		TwitterProperties properties = TwitterProperties.getInstance();
			
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true)
				.setDebugEnabled(false)
				.setOAuthConsumerKey(
						properties.getProperty("oauth.consumerKey"))
				.setOAuthConsumerSecret(
						properties.getProperty("oauth.consumerSecret"))
				.setOAuthAccessToken(
						properties.getProperty("oauth.accessToken"))
				.setOAuthAccessTokenSecret(
						properties.getProperty("oauth.accessTokenSecret"))
				.setGZIPEnabled(true);
			
		TwitterFactory tf = new TwitterFactory(cb.build());
			
        return tf.getInstance();
	}
	
	
	//stampa su file la timeline
	private static void printOnFileHomeTimeline(Twitter twitter, String user, String fileOut){
			
		BufferedWriter gzfile=null;
		
		try {
			
			gzfile = IOUtils.getPlainOrCompressedUTF8Writer(fileOut); 
			ProgressLogger pl = new ProgressLogger( "{} tweets dumped", 50);
			
			ResponseList<Status> rl=twitter.getUserTimeline(user, new Paging(1,50));
			
			for(int i=1; i<rl.size();){
				
				for(Status t:rl){
					pl.up();
					String row = DataObjectFactory.getRawJSON(t);
					gzfile.write(row);
					gzfile.write("\n");
				}
				
				rl=twitter.getUserTimeline(user, new Paging(++i,50));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		finally{
			try {
				gzfile.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}
