package it.cnr.isti.hpc.twitter.domain;

import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.twitter.domain.JsonTweet.Entity.Hashtag;
import it.cnr.isti.hpc.twitter.util.InvalidTweetException;

import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * General attributes of a tweet
 */
public class JsonTweet extends BasicTweet implements Tweet {

	private static final Logger logger = LoggerFactory
			.getLogger(JsonTweet.class);
	/** google json parser */
	public static Gson gson = new Gson();

	private Entity entities; // contains the hashtags (#), user mentions (@) and
								// URLS
	private User user;
	private String created_at;

	private Geo geo;

	private Place place;

	@Override
	public String getText() {
		return text;
	}

	// public boolean isEnglish() {
	// return getLanguage().equals("en");
	// }
	//
	// public boolean isItalian() {
	// return getLanguage().equals("it");
	// }

	// public List<String> getNotBlackListedHashtags() {
	// List<String> hashtags = new LinkedList<String>();
	// for (String s : getHashtagsList()) {
	// if (!Hashtags.inBlacklist(s)) {
	// hashtags.add(s);
	// }
	// }
	// return hashtags;
	// }

	// public String getLanguage() {
	// return Text.getLanguage(text);
	// }

	// public List<String> getInLineHashtags() {
	// List<String> hashtags = new LinkedList<String>(getHashtagsList());
	// hashtags.removeAll(getContextHashtags());
	// return hashtags;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#getContextHashtags()
	 */

	public boolean hasGeo() {
		return geo != null;
	}

	public boolean hasPlace() {
		return place != null;
	}

	public void setText(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#getCreated_at()
	 */

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Date parseDate() throws ParseException {
		DateFormat formatter = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		Date data = formatter.parse(getCreated_at());
		return data;
	}

	public long getShortDate() throws ParseException {

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyyMMdd");

		return Long.parseLong(simpleDateformat.format(parseDate()).toString());

	}

	@Override
	public long getDateInMilliseconds() {
		try {
			return parseDate().getTime();
		} catch (ParseException e) {
			logger.error("parsing the date ({}) ", e.toString());
			return 0;
		}

	}

	/**
	 * getters and setter for the class users. creates also the class user with
	 * the attribute username, with corresponding getter and setters
	 * 
	 * @return
	 */
	public User getUsers() {
		return user;
	}

	public void setUsers(User users) {
		this.user = users;
	}

	public String getUsername() {
		return user.getScreen_name();
	}

	public class User {
		private String name;
		private String screen_name;

		public String getScreen_name() {
			return screen_name;
		}

		public void setScreen_name(String screen_name) {
			this.screen_name = screen_name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	private transient List<String> _ht; // list of lowercased hashtags

	@Override
	public List<String> getHashtagsList() {
		if (_ht != null)
			return _ht;
		_ht = new ArrayList<String>();
		if (getEntities() == null)
			return _ht;
		for (Hashtag h : getEntities().getHashtags()) {
			_ht.add(h.getText().toLowerCase());
		}
		return _ht;
	}

	// /**
	// * Generates a dictionary with the conversion of the hashtags segmented.
	// <br>
	// * example <br>
	// * wishyouwerehere -> with you were here diego -> diego isticnr -> isti
	// cnr
	// *
	// * @param hs
	// * - the hashtag segmenter
	// * @return the dictionary with
	// */
	// public Map<String, String> getSegmentedHashtag(HashtagSegmenter hs) {
	// Map<String, String> ht = new HashMap<String, String>();
	// for (String hashtag : getRawHashtagsList()) {
	// ht.put(hashtag, hs.segment(hashtag));
	// }
	// return ht;
	// }
	//
	// public Map<String, String> getSegmentedHashtag() {
	//
	// Map<String, String> ht = new HashMap<String, String>();
	// for (String hashtag : getRawHashtagsList()) {
	// ht.put(hashtag, hs.segment(hashtag));
	// }
	// return ht;
	// }
	//
	// public Map<String, String> segmentHashtags() throws IOException {
	// Map<String, String> htlist = new HashMap<String, String>();
	// Map<String, String> camelhashtag = loadCamelHashtags();
	// Map<String, String> dic = loadDictionary();
	//
	// for (String hashtag : getRawHashtagsList()) {
	// // System.out.println(hashtag);
	// if (dic.containsKey(hashtag.toLowerCase())) {
	// htlist.put(hashtag, hashtag);
	// } else {
	// try {
	// if (camelhashtag.containsKey(hashtag.toLowerCase())) {
	// htlist.put(hashtag, camelhashtag.get(hashtag));
	// }
	// } catch (Exception e) {
	// logger.info("Hashtag not in doctionary or camel file!");
	// }
	//
	// }
	// }
	// return htlist;
	//
	// }
	//
	// public Map<String, String> loadDictionary() throws IOException {
	// Map<String, String> dic = new HashMap<String, String>();
	// FileInputStream fstream = new FileInputStream(
	// "./src/test/resources/american-english");
	// // Get the object of DataInputStream
	// DataInputStream in = new DataInputStream(fstream);
	// BufferedReader br = new BufferedReader(new InputStreamReader(in,
	// "UTF-8"));
	// String strLine;
	// // Read File Line By Line
	// while ((strLine = br.readLine()) != null) {
	// dic.put(strLine, strLine);
	// }
	// in.close();
	// return dic;
	// }
	//
	// public Map<String, String> loadCamelHashtags() throws IOException {
	// Map<String, String> camelHashtag = new HashMap<String, String>();
	// FileInputStream fstream = new FileInputStream(
	// "./src/test/resources/camel_hashtag.txt");
	// // Get the object of DataInputStream
	// DataInputStream in = new DataInputStream(fstream);
	// BufferedReader br = new BufferedReader(new InputStreamReader(in,
	// "UTF-8"));
	// String strLine;
	// // Read File Line By Line
	// while ((strLine = br.readLine()) != null) {
	// try {
	// camelHashtag.put(strLine.toLowerCase(), hs.segment(strLine));
	// } catch (Exception e) {
	// System.out.println("Exception at hashtag" + strLine);
	// }
	// }
	// in.close();
	// return camelHashtag;
	// }
	//
	// /**
	// * returns the text of the twit, replacing the hashtags with thir
	// segmented
	// * hashtags.<br />
	// * <b> example </b> twit: #heaven from Hell, i #wishyouwerehere <br />
	// * returned value: heaven from Hell, i wish you were here <br/>
	// *
	// * the conversion is done using a map that contains for an hashtag its
	// * segmentation. If the map does not contain the segmentation, the hashtag
	// * is not segmented (only the sharp char (#) is removed).
	// *
	// * @param segmentedHashtag
	// * - a map containing for each hashtag its segmentation
	// * @return the text of the twit with the hashtags segmented.
	// * @throws InvalidTweetException
	// * @throws IOException
	// */
	// public String getTextWithSegmentedHashtag(
	// Map<String, String> segmentedHashtag) throws InvalidTweetException,
	// IOException {
	// if (dictionary == null)
	// dictionary = Terms.loadTrainingTerms();
	// StringBuilder sb = new StringBuilder();
	// for (String t : getText().split(" ")) {
	// if ((t.length() > 1) && (t.charAt(0) == '#')) {
	// String key = t.substring(1).toLowerCase();
	// if (segmentedHashtag.containsKey(key)) {
	// sb.append(segmentedHashtag.get(key)).append(" ");
	// } else {
	// if (dictionary.contains(key)) {
	// sb.append(key).append(" ");
	// }
	// }
	// } else
	// sb.append(t).append(" ");
	// }
	// return sb.toString();
	// }
	//
	// /**
	// * returns the text of the twit, replacing the hashtags with thir
	// segmented
	// * hashtags.<br />
	// * <b> example </b> twit: #heaven from Hell, i #wishyouwerehere <br />
	// * returned value: heaven from Hell, i wish you were here <br/>
	// *
	// * the conversion is done using a map that contains for an hashtag its
	// * segmentation. If the map does not contain the segmentation, the hashtag
	// * is not segmented (only the sharp char (#) is removed).
	// *
	// * @param segmentedHashtag
	// * - a map containing for each hashtag its segmentation
	// * @param deleteContextHashtags
	// * - remove the context hashtags, hashtags occurring at the end
	// * of the tqwit
	// * @return the text of the twit with the hashtags segmented.
	// * @throws InvalidTweetException
	// * @throws IOException
	// */
	// public String getTextWithSegmentedHashtag(
	// Map<String, String> segmentedHashtag, boolean deleteContextHashtags)
	// throws InvalidTweetException, IOException {
	// if (!deleteContextHashtags)
	// return getTextWithSegmentedHashtag(segmentedHashtag);
	// if (dictionary == null)
	// dictionary = Terms.loadTrainingTerms();
	// StringBuilder sb = new StringBuilder();
	// String[] tmp = getText().split("[ .,]");
	// List<String> terms = new LinkedList<String>();
	// boolean found = false;
	// for (int i = tmp.length - 1; i >= 0; i--) {
	// if (tmp[i].isEmpty())
	// continue;
	// if (found) {
	// terms.add(0, tmp[i]);
	// continue;
	// }
	// if (tmp[i].charAt(0) == '#')
	// continue;
	// terms.add(0, tmp[i]);
	// found = true;
	// }
	// for (String t : terms) {
	// if ((t.length() > 1) && (t.charAt(0) == '#')) {
	// String key = t.substring(1).toLowerCase();
	// if (segmentedHashtag.containsKey(key)) {
	// sb.append(segmentedHashtag.get(key)).append(" ");
	// } else {
	// if (dictionary.contains(key)) {
	// sb.append(key).append(" ");
	// } else {
	// if (hs.isHashtagInEncodedFormat(key)) {
	// String value = hs.segment(key);
	// sb.append(value).append(" ");
	// segmentedHashtag.put(key, value);
	// }
	// }
	// }
	// } else
	// sb.append(t).append(" ");
	// }
	// return sb.toString();
	// }

	@Override
	public List<String> getRawHashtagsList() {
		List<String> ht = new ArrayList<String>();
		if (getEntities() == null)
			return ht;
		for (Hashtag h : getEntities().getHashtags()) {
			ht.add(h.getText());
		}
		return ht;
	}

	/**
	 * Takes in input the json describing a tweet and returns an Tweet object
	 * with the fields initialized.
	 * 
	 * @param tweet
	 *            - a reader with the json string describing the tweet
	 * @return the object tweet inizialized with the values in the json
	 * @throws InvalidTweetException
	 *             if the json is not legal or it is malformed
	 */
	public static JsonTweet parseTwitFromJson(Reader tweet)
			throws InvalidTweetException {
		JsonTweet t = null;
		try {
			t = gson.fromJson(tweet, JsonTweet.class);
		} catch (Exception e) {
			logger.error("error parsing the tweet ({}) ", e.getMessage());
			throw new InvalidTweetException("parsing the tweet");
		}
		// if (!t.isLegal()) {
		// throw new InvalidTweetException("invalid tweet");
		// }
		t.removeNewLinesAndTabs();
		return t;
	}

	/**
	 * Takes in input the json describing a tweet and returns an Tweet object
	 * with the fields initialized.
	 * 
	 * @param tweet
	 *            - the json string describing a tweet
	 * @return the object tweet inizialized with the values in the json
	 * @throws InvalidTweetException
	 *             if the json is not legal or it is malformed
	 */
	public static JsonTweet parseTweetFromJson(String tweet)
			throws InvalidTweetException {
		JsonTweet t = null;
		try {
			t = gson.fromJson(tweet, JsonTweet.class);
		} catch (Exception e) {
			logger.error("error parsing the tweet ({}) ", e.getMessage());
			throw new InvalidTweetException("parsing the tweet");
		}
		// if (!t.isLegal()) {
		// throw new InvalidTweetException("invalid tweet");
		// }
		// t.removeNewLinesAndTabs();
		return t;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#getCleanTwit()
	// */
	// public String getCleanTwit() throws InvalidTweetException {
	// String cleanTwit = "";
	// if (!isLegal())
	// throw new InvalidTweetException();
	// if (hs == null)
	// try {
	// loadHashtagSegmenter();
	// } catch (IOException e1) {
	// logger.error("Error loading the segmenter");
	// System.exit(-1);
	// }
	//
	// Map<String, String> segmentedHashtag = getSegmentedHashtag(hs);
	// try {
	// cleanTwit = getTextWithSegmentedHashtag(segmentedHashtag, true);
	// } catch (IOException e) {
	// logger.error("error segmenting hashtags");
	// cleanTwit = getTweetWithoutHashtags();
	// }
	// cleanTwit = cleanQuery(cleanTwit);
	// if (cleanTwit.isEmpty())
	// throw new InvalidTweetException(" cleaned twit is empty  (twit =  "
	// + getText() + ")");
	// return cleanTwit;
	// }

	/*
	 * // * (non-Javadoc) // * // * @see // *
	 * it.cnr.isti.hpc.twitter.domain.TweetInterface#getTweetWithoutHashtags()
	 * //
	 */
	// public String getTweetWithoutHashtags() throws InvalidTweetException {
	// String text = getText().toLowerCase();
	// text = text.replaceAll("#[^ .]+", " ");
	// return text;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#isRetweet()
	 */
	// public boolean isRetweet() {
	// try {
	// return getText().toLowerCase().startsWith("rt");
	// } catch (InvalidTweetException e) {
	//
	// }
	// return false;
	// }
	//
	// public static boolean startsWithRetweet(String s) {
	// return s.toLowerCase().startsWith("rt");
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#isOnlyURL()
	// */
	// public boolean isOnlyURL() throws IOException, InvalidTweetException {
	//
	// String[] items = getText().split("[ 	]");
	// int i = 0;
	// for (String s : items) {
	// if (!s.isEmpty()) {
	// try {
	// URL url = new URL(s);
	// i++;
	// } catch (MalformedURLException e) {
	// return false;
	// }
	// }
	// }
	//
	// return true;
	//
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// it.cnr.isti.hpc.twitter.domain.TweetInterface#isOnlyURLAndHashtags()
	// */
	// public boolean isOnlyURLAndHashtags() throws IOException,
	// InvalidTweetException {
	// String[] items = getText().split(" ");
	// int i = 0;
	// int j = 0;
	// for (String s : items) {
	// if (!s.isEmpty()) {
	// try {
	// URL url = new URL(s);
	// i++;
	// } catch (MalformedURLException e) {
	// }
	//
	// if (s.charAt(0) == '#') {
	// j++;
	// }
	// }
	// }
	//
	// if ((i > 0) && (j > 0) && (i + j == items.length)) {
	// return true;
	// } else {
	// return false;
	// }
	//
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#hasUrl()
	// */
	// public boolean hasUrl() throws InvalidTweetException, IOException {
	//
	// /*
	// * Text tweetText = new Text(getText()); List<String> terms =
	// * tweetText.getTerms(); int i = 0; for (String s : terms) { try { URL
	// * url = new URL(s); i++; } catch (MalformedURLException e) {
	// *
	// * }
	// *
	// * }
	// *
	// * return i > 0;
	// */
	//
	// String[] items = getText().split("[ 	]");
	// int i = 0;
	// for (String s : items) {
	// if (!s.isEmpty()) {
	// try {
	// URL url = new URL(s);
	// i++;
	// } catch (MalformedURLException e) {
	//
	// }
	// }
	// }
	//
	// return i > 0;
	//
	// }
	//
	// public static boolean containsUrl(String text)
	// throws InvalidTweetException, IOException {
	//
	// String[] items = text.split("[ 	]");
	// int i = 0;
	// for (String s : items) {
	// if (!s.isEmpty()) {
	// try {
	// URL url = new URL(s);
	// i++;
	// } catch (MalformedURLException e) {
	//
	// }
	// }
	// }
	//
	// return i > 0;
	//
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#hasMentions()
	// */
	// public boolean hasMentions() throws InvalidTweetException, IOException {
	// String[] items = getText().split("[ 	]");
	// int i = 0;
	// for (String s : items) {
	//
	// if ((s.charAt(0) == '@') && (s.length() > 1)) {
	// // System.out.println("|"+s+"|");
	// return true;
	// }
	// }
	//
	// return false;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#beginsWithMentions()
	// */
	// public boolean beginsWithMentions() throws InvalidTweetException,
	// IOException {
	// String[] items = getText().split("[ 	]");
	//
	// if ((items[0].charAt(0) == '@') && (items[0].length() > 1)) {
	//
	// return true;
	// } else {
	//
	// return false;
	// }
	// }
	//
	// public static boolean startsWithMentions(String s)
	// throws InvalidTweetException, IOException {
	// String[] items = s.split("[ 	]");
	//
	// if ((items[0].charAt(0) == '@') && (items[0].length() > 1)) {
	//
	// return true;
	// } else {
	//
	// return false;
	// }
	// }

	// /**
	// * Return a tab separed representation of the twit, in order to store the
	// * twits in a plain file and load (@see load) then fast.
	// *
	// * @return a tab separed representation of the twit (text \t hashtags)
	// * @throws InvalidTweetException
	// */
	// public String dump() throws InvalidTweetException {
	// if (!isLegal())
	// throw new InvalidTweetException();
	// StringBuilder sb = new StringBuilder();
	// String text = getText().replaceAll("\n", " ");
	// text = text.replaceAll("\r", " ");
	// text = text.replaceAll("\t", " ");
	// sb.append(text).append("\t");
	// if (getEntities() != null) {
	// for (Hashtag h : getEntities().getHashtags()) {
	// sb.append(h.getText()).append(" ");
	// }
	// }
	// return sb.toString();
	// }

	private JsonTweet() {
	}

	// /**
	// * returns the hashtag in couples (if the twits contains more than 2
	// * hashtag). This function is used to build the graph of the hashtag
	// * cooccuring together. Eacg element in the list contains a couple of hash
	// * tab separed by a tab. Each couple contains two hashtag x,y if x is
	// before
	// * than y in the twit. example: twit: my name is #asd and #today I'm
	// #happy
	// * asd \t today asd \t happy today \t happy
	// *
	// * @return a list of string, each string containing a couple of hash tag
	// * separed by a tab
	// */
	// public List<String> getHashtagCouples() {
	// List<String> ht = getHashtagsList();
	// List<String> couples = new ArrayList<String>(ht.size());
	// if (ht.size() < 2)
	// return couples;
	// for (int left = 0; left < ht.size() - 1; left++) {
	// for (int right = left + 1; right < ht.size(); right++) {
	// // add the line only if the hashtags are different
	// if (!ht.get(left).equals(ht.get(right))) {
	// couples.add(ht.get(left) + "\t" + ht.get(right));
	// }
	// }
	// }
	// return couples;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.cnr.isti.hpc.twitter.domain.TweetInterface#getIndexLine()
	 */
	public String getIndexLine() {
		return "";
		// if (hs == null)
		// loadHashtagSegmenter();
		// StringBuilder sb = new StringBuilder();
		// String cleanText = getCleanTwit(text);
		// if (!isLegal())
		// throw new InvalidTweetException();
		// if (!isLegal(cleanText))
		// throw new InvalidTweetException();
		//
		// Map<String, String> segmentedHashtag = getSegmentedHashtag(hs);
		//
		// for (Hashtag ht : entities.getHashtags()) {
		// String hashtag = getCleanTwit(ht.getText());
		// if (!hashtag.isEmpty()) {
		// sb.append(hashtag).append("\t").append(cleanText).append("\t")
		// .append(getRelatedHashtags(hashtag)).append("\t");
		// sb.append(
		// getCleanTwit(getTextWithSegmentedHashtag(segmentedHashtag)))
		// .append("\t");
		// for (String t : segmentedHashtag.values())
		// sb.append(t).append(", ");
		// sb.append("\n");
		// }
		// }
		//
		// return sb.toString();

	}

	// /**
	// * Loads the hashtag segmenter
	// *
	// * @throws IOException
	// * if there are problems reading the file with the segmentation
	// */
	// private void loadHashtagSegmenter() throws IOException {
	// hs = CamelHashtagSegmenter.getInstance();
	// }

	// the part below is only to parse the json with gson.

	public Entity getEntities() {
		return entities;
	}

	public void setEntities(Entity entities) {
		this.entities = entities;
	}

	public class Entity {

		private List<Hashtag> hashtags;

		public List<Hashtag> getHashtags() {
			return hashtags;
		}

		public void setHashtags(List<Hashtag> hashtags) {
			this.hashtags = hashtags;
		}

		// hashtags of a twit, we extract only the text and no other info
		public class Hashtag {

			public Hashtag(String text) {
				this.text = text;
			}

			private String text;

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + getOuterType().hashCode();
				result = prime * result
						+ ((text == null) ? 0 : text.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Hashtag other = (Hashtag) obj;
				if (!getOuterType().equals(other.getOuterType()))
					return false;
				if (text == null) {
					if (other.text != null)
						return false;
				} else if (!text.equals(other.text))
					return false;
				return true;
			}

			public String getText() {
				return text;
			}

			public void setText(String text) {
				this.text = text;
			}

			private Entity getOuterType() {
				return Entity.this;
			}
		}
	}

	private static class Geo {
		String type;
		List<Float> coordinates;
	}

	private static class Place {
		String id;
		BoundingBox bounding_box;
		String place_type;
		String name;
		String country;
		String full_name;
	}

	private static class BoundingBox {
		String type;
		List<List<List<Float>>> coordinates;
	}

	public static void main(String[] args) throws InvalidTweetException {
		String json = IOUtils.getFileAsString("/tmp/test.json");
		JsonTweet t = JsonTweet.parseTweetFromJson(json);
		System.out.println(gson.toJson(t));
	}

}
