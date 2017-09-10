package edu.carleton.comp4601.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@XmlRootElement
public class Document {
	private Integer id;
	private Float score;
	private String name;
	private String text;
	private ArrayList<String> tags;
	private ArrayList<String> links;

	public Document() {
		tags = new ArrayList<String>();
		links = new ArrayList<String>();
	}

	public Document(Integer id) {
		this();
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public Document(Map<?, ?> map) {
		this();
		this.id = (Integer) map.get("id");
		this.score = (Float) map.get("score");
		this.name = (String) map.get("name");
		this.text = (String) map.get("text");
		this.tags = (ArrayList<String>) map.get("tags");
		this.links = (ArrayList<String>) map.get("links");
	}

	public Document(Integer id2, String name2, Float score2, String text2) {
		this.id = id2; //(Integer) map.get("id");
		this.score = score2;// (Float) map.get("score");
		this.name = name2;// (String) map.get("name");
		this.text = text2;//(String) map.get("text");
	}

	public Integer getId() {
		return id;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Float getScore() {
		return score;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public ArrayList<String> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<String> links) {
		this.links = links;
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public void removeTag(String tag) {
		tags.remove(tag);
	}

	public void addLink(String link) {
		links.add(link);
	}

	public void removeLink(String link) {
		links.remove(link);
	}
	public DBObject toDBObject() {
		BasicDBObject o = new BasicDBObject();
		o.put("id", getId());
		o.put("name", getName());
		o.put("text", getText());
		o.put("score", getScore());
		o.put("tags", getTags());
		o.put("links", getLinks());
		return o;
}
}