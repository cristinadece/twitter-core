/**
 *  Copyright 2011 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package it.cnr.isti.hpc.twitter.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TwitterProperties.java
 * 
 * @author Diego Ceccarelli, diego.ceccarelli@isti.cnr.it created on 25/set/2011
 */
public class TwitterProperties {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(TwitterProperties.class);

	private static final String TWITTER_PROPERTIES_FILE = "twitter.properties";

	Properties properties;
	private static TwitterProperties twitterProperties;

	private TwitterProperties()  {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(TWITTER_PROPERTIES_FILE));
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the property file "
					+ TWITTER_PROPERTIES_FILE + " in the current folder ");
			System.exit(-1);
		}catch (IOException e1) {
			System.err.println("Problems with the property file "
					+ TWITTER_PROPERTIES_FILE +  " ( "+ e1 +" ) ");
			System.exit(-1);
		}
		
	}

	public static TwitterProperties getInstance() {
		if (twitterProperties == null)
			twitterProperties = new TwitterProperties();
		return twitterProperties;
	}

	public String getProperty(String key) {
		//logger.info("loading property {} value: {}", key,
			//	properties.getProperty(key));
		return properties.getProperty(key);
	}
	
	public String getBigramsFile(){
		return getProperty("bigrams");
	}
	
	public String getTrainingSetFile(){
		return getProperty("training");
	}
	
	public String getDict(){
		return getProperty("dict");
	}
	
	
}
