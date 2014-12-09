/**
 * 
 */
package it.cnr.isti.hpc.twitter.trends.output;

import java.util.Date;

/**
 * @author cris
 *
 */
public class EventTag {
	private int id;
	private String name;
	private String tagCount;
	private Date createDate;
	private Date lastUpdate;
	
	

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

	public String getTagCount() {
		return tagCount;
	}

	public void setTagCount(String tagCount) {
		this.tagCount = tagCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * 
	 */
	public EventTag() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
