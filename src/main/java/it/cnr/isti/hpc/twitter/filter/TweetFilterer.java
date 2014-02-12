/**
 *  Copyright 2014 Diego Ceccarelli
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

/**
 *  Copyright 2014 Diego Ceccarelli
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
package it.cnr.isti.hpc.twitter.filter;

import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.log.ProgressLogger;
import it.cnr.isti.hpc.twitter.domain.JsonTweet;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Feb 6, 2014
 */
public class TweetFilterer {

	private static final Logger logger = LoggerFactory
			.getLogger(TweetFilterer.class);

	public static final String DATE_FORMAT_NOW = "yyyyMMdd";
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

	Map<String, Filter> filters = new HashMap<String, Filter>();
	public File dumpDir = new File(".");

	/**
	 * @param dumpDirectory
	 *            the directory collecting all the folder with the filtered
	 *            tweets.
	 */
	public TweetFilterer(String dumpDirectory) {
		this();
		dumpDir = new File(dumpDirectory);
	}

	public boolean canProcess(String filename) {
		Date date = getDate(filename);
		if (date == null) {
			logger.warn("no date for file {} ", filename);
			return false;
		}
		Date now = Calendar.getInstance().getTime();
		// must be greater than 25 hours
		return now.getTime() - date.getTime() > 90000000; // 60*60*25*10000;

	}

	public boolean yetProcessed(String filter, String filename) {
		Date d = getDate(filename);
		Filter f = filters.get(filter);
		File output = getOutput(f, d);
		return output.exists();
	}

	private Date getDate(String filename) {
		String pattern = "[0-9]{8}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(filename);
		Date date = null;
		if (m.find()) {
			String d = m.group(0);
			try {
				date = sdf.parse(d);
			} catch (ParseException e) {
				logger.error("parsing date in {} ", filename);
			}
		}
		return date;
	}

	public TweetFilterer() {
		addFilter(new EnglishFilter());
		addFilter(new ItalianFilter());
		addFilter(new GeoreferencedFilter());
	}

	public Collection<Filter> getFilters() {
		return filters.values();
	}

	public boolean hasFilter(String name) {
		return filters.containsKey(name);
	}

	public void addFilter(Filter f) {
		filters.put(f.getName(), f);
	}

	private File getOutput(Filter f, Date d) {
		File directory = new File(dumpDir, f.getName());
		File outputFile = new File(directory, f.getName() + "-" + sdf.format(d)
				+ ".json.gz");
		return outputFile;
	}

	public void filter(File input, String filter) throws IOException {
		Date d = getDate(input.getName());
		Filter f = filters.get(filter);
		if (f == null) {
			logger.error("cannot find filter '{}' ", filter);
			return;
		}
		File directory = new File(dumpDir, f.getName());
		if (!directory.exists()) {
			logger.info("creating directory '{}'", directory);
			directory.mkdirs();
		}
		File outputFile = getOutput(f, d);

		BufferedReader br = IOUtils.getPlainOrCompressedUTF8Reader(input
				.getAbsolutePath());

		BufferedWriter bw = IOUtils.getPlainOrCompressedUTF8Writer(outputFile
				.getAbsolutePath());

		ProgressLogger pl = new ProgressLogger("processed {} tweets filter: "
				+ f.getName(), 100000);

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			pl.up();
			try {
				JsonTweet tweet = JsonTweet.parseTweetFromJson(line);
				if (f.isLegal(tweet)) {
					bw.write(line);
					bw.newLine();
				}
			} catch (InvalidTweetException e) {
				logger.warn("parsing tweet {} ", line);
				continue;
			}

		}
		logger.info("FINISH processing file {}", input);

		bw.close();

	}
}
