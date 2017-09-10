package edu.carleton.comp4601.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@XmlRootElement
public class Movie {
	private Integer id;
	private String name;
	private String description;
	private Integer revNo;
	private Integer ratings;
	private ArrayList<User> users;
	
	

	public Movie() {
		users =  new ArrayList<User>();
	}

	public Movie(Integer id) {
		this();
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public Movie(Map<?, ?> map) {
		this();
		this.id = (Integer) map.get("id");
		this.name = (String) map.get("name");
		this.description = (String) map.get("description");
		this.ratings = (Integer) map.get("ratings");
		this.revNo =  (Integer) map.get("revNo");
		this.users = (ArrayList<User>) map.get("users");
	}

	public Movie(Integer id2, String name2,String des,String img,ArrayList<String> tag,ArrayList<String> cat) {
		this.id = id2; 
		this.name = name2;
		this.description = des;
	}
	public Movie(Integer id2, String name2) {
		this.id = id2; 
		this.name = name2;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRatings() {
		return ratings;
	}
	public void setRevNo(Integer no) {
		this.revNo= no;
	}
	public Integer getRevNo() {
		return revNo;
	}
	public Integer increaseRev(){
		if(this.revNo == -1){
			setRevNo((0+1));
		}else{
			setRevNo((this.revNo+1));
		}
		return getRevNo();
	}
	
	
	public void setRatings(Integer rat) {
		this.ratings = rat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String name) {
		this.description = name;
	}
	
	public ArrayList<User> getusers() {
		return users;
	}

	public void setusers(ArrayList<User> users) {
		this.users = users;
	}



	public DBObject toDBObject() {
		BasicDBObject o = new BasicDBObject();
		o.put("id", getId());
		o.put("name", getName());
		o.put("description", getDescription());
		o.put("ratings", getRatings());
		o.put("revNo", getRevNo());
		o.put("users", this.getusers());
		return o;
	}
	
}