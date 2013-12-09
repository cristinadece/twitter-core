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
//import it.cnr.isti.hpc.twitter.util.TwitterProperties;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import ch.qos.logback.classic.pattern.Util;
//
///**
// * Hits.java
// *
// * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it
// * created on 30/set/2011
// */
//public class Hits {
//	/**
//	 * Logger for this class
//	 */
//	private static final Logger logger = LoggerFactory.getLogger(Hits.class);
//	
//	Map<String,HubAuthority> values;
//	
//	private static Hits hits;
//	private Hits(){
//		values = new HashMap<String,HubAuthority>();
//	}
//	
//	public static Hits getInstance() throws IOException{
//		if (hits != null ) return hits;
//		logger.info("reading hits...");
//		hits = new Hits();
//		String hubAndAuthorityFile = TwitterProperties.getInstance().getProperty("hits_file");
//		BufferedReader br = Util.getPlainOrCompressedReader(hubAndAuthorityFile);
//		String line="";
//		while ( ( line = br.readLine() ) != null){
//			if (line.isEmpty()) continue;
//			Scanner sc = new Scanner(line).useDelimiter("\t");
//			String ht = sc.next();
//			double auth = sc.nextDouble();
//			double hub = sc.nextDouble();
//			hits.values.put(ht, hits.new HubAuthority(hub, auth));
//		}
//		logger.info("...done");
//		return hits;
//	}
//	
//	public static Hits getInstance(String resource) throws IOException{
//		if (hits != null ) return hits;
//		logger.info("reading hits...");
//		hits = new Hits();
//		String hubAndAuthorityFile = resource;
//		BufferedReader br = Util.getPlainOrCompressedReader(hubAndAuthorityFile);
//		String line="";
//		while ( ( line = br.readLine() ) != null){
//			if (line.isEmpty()) continue;
//			Scanner sc = new Scanner(line).useDelimiter("\t");
//			String ht = sc.next();
//			double auth = Double.parseDouble(sc.next()+"d");
//			double hub = Double.parseDouble(sc.next()+"d");
//			hits.values.put(ht, hits.new HubAuthority(hub, auth));
//		}
//		logger.info("...done");
//		return hits;
//	}
//	
//	public HubAuthority getHubAuthority(String hashtag){
//		if (values.containsKey(hashtag)) return values.get(hashtag);
//		return new HubAuthority(0, 0);
//	}
//	
//	public double getHub(String hashtag){
//		if (values.containsKey(hashtag)) return values.get(hashtag).getHub();
//		return 0;
//	}
//	
//	public double getAuthority(String hashtag){
//		if (values.containsKey(hashtag)) return values.get(hashtag).getAuthority();
//		return 0;
//	}
//	
//	
//	public class HubAuthority{
//		
//		
//		private double hub;
//		private double authority;
//		
//		public HubAuthority(double hub, double authority){
//			this.hub = hub;
//			this.authority = authority;
//		}
//		public double getAuthority() {
//			return authority;
//		}
//		public void setAuthority(double authority) {
//			this.authority = authority;
//		}
//		public double getHub() {
//			return hub;
//		}
//		public void setHub(double hub) {
//			this.hub = hub;
//		}
//		@Override
//		public String toString(){
//			return authority+"\t"+hub;
//		}
//		
//		
//	}
//}
