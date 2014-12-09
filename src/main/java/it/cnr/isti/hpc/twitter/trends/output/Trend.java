/**
 * Cristina Muntean Dec 9, 2014
 * twitter-core
 */
package it.cnr.isti.hpc.twitter.trends.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

public class Trend {
	
	private String id;
	private Date atTime;
	private Date circa;
	private String description;
	private String longitude;
	private String latitude;
	private String route;
	private String street_number;
	private String locality;
	private String administrative_area_level_2;
	private String administrative_area_level_1;
	private String country;
	private String posta_code;
	private String severity;
	private Boolean credibility = true;
	private List<String> related;
	private List<List<JsonObject>> eventResource; 
	// a list of resources, represented by tweets 
	// (containing a list of tweets)
	private List<Entity> entities;
	private List<EventTag> eventTag;
	private List<Classified> classified;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Date getAtTime() {
		return atTime;
	}



	public void setAtTime(Date atTime) {
		this.atTime = atTime;
	}



	public Date getCirca() {
		return circa;
	}



	public void setCirca(Date circa) {
		this.circa = circa;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getRoute() {
		return route;
	}



	public void setRoute(String route) {
		this.route = route;
	}



	public String getStreet_number() {
		return street_number;
	}



	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}



	public String getLocality() {
		return locality;
	}



	public void setLocality(String locality) {
		this.locality = locality;
	}



	public String getAdministrative_area_level_2() {
		return administrative_area_level_2;
	}



	public void setAdministrative_area_level_2(String administrative_area_level_2) {
		this.administrative_area_level_2 = administrative_area_level_2;
	}



	public String getAdministrative_area_level_1() {
		return administrative_area_level_1;
	}



	public void setAdministrative_area_level_1(String administrative_area_level_1) {
		this.administrative_area_level_1 = administrative_area_level_1;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getPosta_code() {
		return posta_code;
	}



	public void setPosta_code(String posta_code) {
		this.posta_code = posta_code;
	}



	public String getSeverity() {
		return severity;
	}



	public void setSeverity(String severity) {
		this.severity = severity;
	}



	public Boolean getCredibility() {
		return credibility;
	}



	public void setCredibility(Boolean credibility) {
		this.credibility = credibility;
	}



	public List<String> getRelated() {
		return related;
	}



	public void setRelated(List<String> related) {
		this.related = related;
	}



	public List<List<JsonObject>> getEventResource() {
		return eventResource;
	}



	public void setEventResource(List<List<JsonObject>> eventResource) {
		this.eventResource = eventResource;
	}



	public List<Entity> getEntities() {
		return entities;
	}



	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}



	public List<EventTag> getEventTag() {
		return eventTag;
	}



	public void setEventTag(List<EventTag> eventTag) {
		this.eventTag = eventTag;
	}



	public List<Classified> getClassified() {
		return classified;
	}



	public void setClassified(List<Classified> classified) {
		this.classified = classified;
	}



	public Trend() {
		// TODO Auto-generated constructor stub
		credibility = true;
		related = new ArrayList<String>();
		eventResource = new ArrayList<List<JsonObject>>();
		entities = new ArrayList<Entity>();
		eventTag = new ArrayList<EventTag>();
		classified = new ArrayList<Classified>();
		
	}

}
