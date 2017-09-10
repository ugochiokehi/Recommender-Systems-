package edu.carleton.comp4601.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@XmlRootElement
public class User {

	private Integer id;
	private Integer movieId;
	private String name;
	private Integer rating;


	public User() {
	}

	public User(Integer id) {
		this();
		this.id = id;
	}
	public User(Integer id, Integer res) {
		this();
		this.id = id;
		this.movieId = res;
	}

	@SuppressWarnings("unchecked")
	public User(Map<?, ?> map) {
		this();
		this.id = (Integer) map.get("id");
		this.movieId = (Integer) map.get("movieid");
		this.name = (String) map.get("name");
		this.rating = (Integer) map.get("rating"); 
	}

	public User(Integer id2, String name2) {
		this.id = id2; 
		this.name = name2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer id) {
		this.movieId = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Integer getRatings() {
		return rating;
	}

	public void setRatings(Integer rat) {
		this.rating = rat;
	}

	public DBObject toDBObject() {
		BasicDBObject o = new BasicDBObject();
		o.put("id", getId());
		o.put("movieid", getMovieId());
		o.put("name", getName());
		o.put("rating", getRatings());
		return o;
	}
	public void covert(Object u) {
		//User a = new User();
		setId((Integer)((BasicBSONObject) u).getInt("id"));
		setName((String)((BasicBSONObject) u).getString("name"));
		setMovieId((Integer)((BasicBSONObject) u).getInt("resid"));
		setRatings((Integer)((BasicBSONObject) u).getInt("ratings"));
		//return a;
	}
	public void covert2(Object u) {
		//User a = new User();
		setId((Integer)((BasicBSONObject) u).getInt("id"));
		setName((String)((BasicBSONObject) u).getString("name"));
		setMovieId((Integer)((BasicBSONObject) u).getInt("resid"));
		setRatings((Integer)((BasicBSONObject) u).getInt("rating"));
		//return a;
	}
}