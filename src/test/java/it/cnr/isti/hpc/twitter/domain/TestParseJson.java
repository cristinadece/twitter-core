package it.cnr.isti.hpc.twitter.domain;

//import static org.junit.Assert.*;

import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonSyntaxException;

//import com.google.gson.JsonParseException;



public class TestParseJson {
	transient private static final Logger logger = LoggerFactory
			.getLogger(TestParseJson.class);

	@Test
	@Ignore
	public void test() throws InvalidTweetException {
		InputStream is = 
				TestParseJson.class.getResourceAsStream( "/sample.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String st = "";
        try {
			while ((st = br.readLine()) != null) {
				StringReader myStringReader = new StringReader(st);
				Tweet t = JsonTweet.parseTwitFromJson(myStringReader);
			    System.out.println("Twit: "+t);
			}
		} catch (IOException e) {
			//System.err.println("Error reading from Reader :" + e.getMessage());
		} catch (JsonSyntaxException e1){
        	logger.error("malformed line: {} ({})",st,e1.toString());
        }
        
	}

	@Test
	public void testGetHashtag() throws IOException{
		FileWriter file = new FileWriter("src/main/resources/hashtags.txt");
		BufferedWriter out = new BufferedWriter(file);
		InputStream is = 
				TestParseJson.class.getResourceAsStream( "/sample.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String st = "";
        try {
			while ((st = br.readLine()) != null) {
				StringReader myStringReader = new StringReader(st);
				JsonTweet t = JsonTweet.parseTwitFromJson(myStringReader);
				if (! t.isLegal()) continue;
				for ( String hashtag : t.getHashtagsList() )
					logger.info(hashtag);
				// write hashtag in console							
				System.out.print(t.getIndexLine());
				// write hashtag in file
				out.write(t.getIndexLine());
						
			}
			
		
        }
		catch(Exception e){
			logger.error("Exception during parsing line {} {} ",st,e.toString());
		} 
        out.close();
        
	
	}
	@Test
	public void testUserName() throws IOException, InvalidTweetException{
		InputStream is = 
				TestParseJson.class.getResourceAsStream( "/sample.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		for (String line = br.readLine(); line != null ; line = br.readLine()){
			JsonTweet t = JsonTweet.parseTweetFromJson(line);
			System.out.println(t.getUsername());
		}
	}
	
	
}
