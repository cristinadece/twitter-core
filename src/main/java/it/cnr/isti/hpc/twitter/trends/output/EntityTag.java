package it.cnr.isti.hpc.twitter.trends.output;

import java.util.Date;

public class EntityTag {

	private int id;
	private String name;
	private Date createDate;
	private Date lastUpdate;
	
	public EntityTag(){
		
	}
	
	public EntityTag(String tag){
		name = tag;
	}
	
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
	

}
