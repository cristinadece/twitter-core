/**
 * 
 */
package it.cnr.isti.hpc.twitter.trends.output;

/**
 * @author cris
 *
 */
public class Classified {
	private int id;
	private String name;
	private String uri;
	private String description;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 */
	public Classified() {
		// TODO Auto-generated constructor stub
		name = "trend-detection";
		uri = "http://secure.eng.it/ontologySecure/microEvents.owl#Trend_Detection";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
