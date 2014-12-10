/**
 * 
 */
package it.cnr.isti.hpc.twitter.trends.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cris
 *
 */
public class Entity {
	private int id;
	private Date createDate;
	private Date lastUpdate;
	private List<EntityTag> entityTag;

	/**
	 * 
	 */
	public Entity() {
		// TODO Auto-generated constructor stub
		entityTag = new ArrayList<EntityTag>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<EntityTag> getEntityTag() {
		return entityTag;
	}

	public void setEntityTag(List<EntityTag> entityTag) {
		this.entityTag = entityTag;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
