package edu.carleton.comp4601.dao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


import edu.carleton.comp4601.dao.Document;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieCollection {
	@XmlElement(name="movies")
	MongoClient mc; 
	DB db ;
	DBCollection movieColl;
	DBCollection userColl;
	
	private static MovieCollection movies;
	
	public static MovieCollection getInstance() {
		if (movies == null)
			movies = new MovieCollection();
		return movies;
	}
	public void setmovies(MovieCollection mov) {
		this.movies = mov;
	}
	public MovieCollection() {
		try {
			this.mc   = new MongoClient("localhost",27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.db   =  mc.getDB("project");
		this.movieColl = db.getCollection("moviecollection");
		this.userColl = db.getCollection("usercollection");
	}
	
	public boolean containsKey(Movie doc) throws IOException {
		// TODO Auto-generated method stub
		BasicDBObject query = new BasicDBObject("id",doc.getId());
		
		long result = movieColl.count(query);
		
		if(result >= 1){
			return true;
		}
		else{
			return false;
		}		
	}
//	this.id = (Integer) map.get("id");
//	this.name = (String) map.get("name");
//	this.ratings = (Integer) map.get("ratings");
//	this.image =  (String) map.get("image");
//	this.reviews = (ArrayList<String>) map.get("reviews");
//	this.tags = (ArrayList<String>) map.get("tags");
//	this.category = (ArrayList<String>) map.get("category");
	public boolean addMovie(Integer id, String name, String description) throws IOException{	
			Map<Object, Object> d = new ConcurrentHashMap<>();
			d.put("id", id);
			d.put("name", name);
			d.put("description", description);
			d.put("ratings", 0);
			d.put("revNo", 0);
			d.put("users", (new ArrayList<String>()));
			Movie res = new Movie(d);	
			if(containsKey(res)){
				BasicDBObject query = new BasicDBObject("id", id);
				movieColl.update(query, res.toDBObject());
			}else{
				System.out.println("Adding movie..");
				movieColl.insert(res.toDBObject());
			}
			return true;
	}

	public void updateMovie(Integer id,User rev){			
		BasicDBObject query = new BasicDBObject("id", id);
		BasicDBObject user =new BasicDBObject();
		user.put("id",rev.getId());
		user.put("resid", rev.getMovieId());
		user.put("name", rev.getName());
		user.put("ratings",rev.getRatings());

		BasicDBObject updateObj = new BasicDBObject();			
		updateObj.put("$push", new BasicDBObject("users",user));
		
		System.out.println("Updating movie..");

		movieColl.update(query, updateObj,true,true);		
	}
	public void updateRevNo(Integer id){	
		Movie res = findMovie(id);
		BasicDBObject query = new BasicDBObject("id", id);
		BasicDBObject updateObj = new BasicDBObject();			
		updateObj.put("$set", new BasicDBObject("revNo",res.increaseRev()));
		//updateObj.put("revNo", res.increaseRev());
		
		System.out.println("Updating the Restaurant review..");

		movieColl.update(query, updateObj);		
	}
	public void updateRestRating(Integer id,User rev){			
		BasicDBObject query = new BasicDBObject("id", id);
		BasicDBObject updateObj = new BasicDBObject();			
		//updateObj.put("id", id);
		updateObj.put("$set",  new BasicDBObject("ratings", calculateRating(id,rev)));
		
		System.out.println("Updating the Restaurant rating..");

		movieColl.update(query, updateObj);		
	}

	public Integer calculateRating(Integer id,User use){		
		System.out.println("I am calculating new rating");
		int result = 0;
		Movie mov = findMovie(id);
		System.out.println("rating "+mov.getRatings()+" user "+use.getRatings()+"rev num "+ mov.getRevNo());
		if(mov.getRatings()==0){
			result = (mov.getRatings()+use.getRatings())/1;
		}else{
			result = (mov.getRatings()+use.getRatings())/2;
		}
		System.out.println("finish calculating "+ result);
		return result;
	}
	public int findRating(int id){
		System.out.println(" finding the rating...");
		Movie a = new Movie();
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = movieColl.find(query);
		if(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject) cursor.next();
			
			return obj.getInt("ratings");
		} else {
			System.out.println("Couldn't find ratings");
			cursor.close();
			//return null;	
		}
		return id;
	}
	public boolean addUser(Integer id,Integer resId, String name,Integer ratings) throws IOException{	
		
		Map<Object, Object> d = new ConcurrentHashMap<>();
		d.put("id", id);
		d.put("movieid", resId);
		d.put("name", name);
		d.put("rating", ratings);
		
		User user = new User(d);	
		System.out.println("mongo userid: "+user.getId()+" movieid: "+user.getMovieId()+
				" name: "+user.getName()+" rating: "+user.getRatings());
		System.out.println("Adding user..");
		userColl.insert(user.toDBObject());

		return true;
	}
	

	public List<Movie> getRestaurantList() throws IOException {
		List<Movie> loa = new ArrayList<Movie>();
		DBCursor cursor = movieColl.find();
		while (cursor.hasNext()) {
			Movie a = new Movie();
		    BasicDBObject obj = (BasicDBObject) cursor.next();
		    a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setRatings(obj.getInt("ratings"));
		    a.setusers((ArrayList<User>)obj.get("users"));
		    loa.add(a);
		}
		return  loa;
	}

	public List<User> getUserList() throws IOException {
		List<User> loa = new ArrayList<User>();
		DBCursor cursor = userColl.find();
		while (cursor.hasNext()) {
			User a = new User();
		    BasicDBObject obj = (BasicDBObject) cursor.next();
		    a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setMovieId(obj.getInt("movieid"));
		    a.setRatings(obj.getInt("rating"));
		    
		    loa.add(a);
		}
		return  loa;
	}
//	public Document find(int id) throws IOException {
//		Document a = new Document();
//		BasicDBObject query = new BasicDBObject("id",id);
//		DBCursor cursor = coll.find(query);
//		while (cursor.hasNext()) {
//		    BasicDBObject obj = (BasicDBObject) cursor.next();
//		    a.setId(obj.getInt("id"));
//		    a.setName(obj.getString("name"));
//		    a.setScore((float)obj.getInt("score"));
//		    a.setText(obj.getString("text"));
//		}
//		return  a;
//	}

	public Movie findMovie(int id){
		System.out.println("finding the movie");
		Movie a = new Movie();
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = movieColl.find(query);
		if(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject) cursor.next();
			a.setId((Integer)obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setRatings((Integer)obj.getInt("ratings"));
		    a.setRevNo((Integer)obj.getInt("revNo"));
		    System.out.println("rating "+a.getRatings()+"rev "+ a.getRevNo());
		    a.setusers((ArrayList<User>)obj.get("users"));
			cursor.close();
			return a;
		} else {
			System.out.println("Couldn't find doc");
			cursor.close();
			return null;	
		}
	}
	

	public User findUser(int id){
		System.out.println("finding the user");
		User a = new User();
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = userColl.find(query);
		if(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject) cursor.next();
			a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setMovieId(obj.getInt("resid"));
		    a.setRatings(obj.getInt("ratings"));
			cursor.close();
			return a;
		} else {
			System.out.println("Couldn't find user");
			cursor.close();
			return null;	
		}
	}
	
	
	public boolean run(User rev) throws IOException{	
			addUser(rev.getId(),rev.getMovieId(),rev.getName(),rev.getRatings());
			updateMovie(rev.getMovieId(),rev);
			updateRevNo(rev.getMovieId());
			updateRestRating(rev.getMovieId(),rev);
			//addUser(rev);
			return true;
		}
	

//	public void update(Integer id, Float score, String name, String text,
//			ArrayList<String> tags, ArrayList<String> links){			
//			BasicDBObject query = new BasicDBObject("id", id);
//			BasicDBObject updateObj = new BasicDBObject();			
//			updateObj.put("id", id);
//			updateObj.put("score", score);
//			updateObj.put("name", name);
//			updateObj.put("text", text);
//			updateObj.put("tags", tags);
//			updateObj.put("links", links);
//			
//			System.out.println("Updating..");
//			
//			coll.update(query, updateObj);		
//	}


//	public boolean close(int id) throws IOException{
//		if (find(id) != null) {
//			BasicDBObject query = new BasicDBObject("id",id);
//			//DBCursor cursor = (DBCursor) 
//			coll.findAndRemove(query);
//			return true;
//		}
//		else
//			return false;
//	}

}