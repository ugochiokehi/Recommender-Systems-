package edu.carleton.comp4601.dao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
public class DocumentCollection {
	@XmlElement(name="documents")
	MongoClient mc; 
	DB db ;
	DBCollection coll;
	//MongoDatabase db1;
	
	private static DocumentCollection documents;
	//private List<Document> documents;

	/*public List<Document> getDocuments() {
		return documents;
	}*/
	public static DocumentCollection getInstance() {
		if (documents == null)
			documents = new DocumentCollection();
		return documents;
	}
	public void setDocuments(DocumentCollection documents) {
		this.documents = documents;
	}
	public DocumentCollection() {
		try {
			this.mc   = new MongoClient("localhost",27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.db   =  mc.getDB("sda");
		this.coll = db.getCollection("documentcollection");
	}
	public List<Document> getList() throws IOException {
		List<Document> loa = new ArrayList<Document>();
		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			Document a = new Document();
		    BasicDBObject obj = (BasicDBObject) cursor.next();
		    a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setScore((float)obj.getInt("score"));
		    a.setText(obj.getString("text"));
		    a.setLinks((ArrayList<String>)obj.get("links"));
		    a.setTags((ArrayList<String>)obj.get("tags"));
		    loa.add(a);
		}
		return  loa;
	}
	public Document find(int id) throws IOException {
		Document a = new Document();
		BasicDBObject query = new BasicDBObject("id",id);
		DBCursor cursor = coll.find(query);
		while (cursor.hasNext()) {
		    BasicDBObject obj = (BasicDBObject) cursor.next();
		    a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setScore((float)obj.getInt("score"));
		    a.setText(obj.getString("text"));
		}
		return  a;
	}
	public Document findDocument(int id){
		Document a = new Document();
		BasicDBObject query = new BasicDBObject("id", id);
		DBCursor cursor = coll.find(query);
		if(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject) cursor.next();
			a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setScore((float)obj.getInt("score"));
		    a.setText(obj.getString("text"));
		    a.setLinks((ArrayList<String>)obj.get("links"));
		    a.setTags((ArrayList<String>)obj.get("tags"));
			cursor.close();
			return a;
		} else {
			System.out.println("Couldn't find doc");
			cursor.close();
			return null;	
		}
	}
	public boolean containsKey(Document doc) throws IOException {
		// TODO Auto-generated method stub
		BasicDBObject query = new BasicDBObject("id",doc.getId());
		
		long result = coll.count(query);
		
		if(result >= 1){
			return true;
		}
		else{
			return false;
		}		
	}
	
	public void update(Integer id, String name, Float score, String text) {
		BasicDBObject newDocument = new BasicDBObject();
	    newDocument.put("name", name);
	    newDocument.put("score", score);
	    newDocument.put("text", text);
		BasicDBObject searchQuery = new BasicDBObject().append("id",id);
		coll.update(searchQuery, newDocument);		
	}
	
	public Document open(Integer id, String name, Float score, String text) {
		// TODO Auto-generated method stub
		Document a = new Document(id, name,score, text);
		BasicDBObject doc = new BasicDBObject();
		doc.put("id",id);
		doc.put("name", score);
		doc.put("score", score);
		doc.put("text", text);
		this.coll.insert(doc);
		return a;
	}
	
	public boolean open(Integer id, Float score, String name, String text,
			ArrayList<String> tags, ArrayList<String> links) throws IOException{	
			Map<Object, Object> d = new ConcurrentHashMap<>();
			d.put("id", id);
			d.put("score", score);
			d.put("name", name);
			d.put("text", text);
			d.put("tags", tags);
			d.put("links", links);
			Document doc = new Document(d);	
			if(containsKey(doc)){
				BasicDBObject query = new BasicDBObject("id", id);
				coll.update(query, doc.toDBObject());
			}else{
				System.out.println("Adding document..");
				coll.insert(doc.toDBObject());
			}
			return true;
	}
	
	public ArrayList<Document> searchDocumentTags(String tags) throws IOException{ 	
		ArrayList<Document> documents = new ArrayList<Document>(); 	
		ArrayList<String> tagArr= new ArrayList<String>() ;
		String m = "";
		for(int i=0; i<= tags.length()-1; i++){
			if(tags.charAt(i)==':'){
				tagArr.add(m);
				m="";
			}
			else{
				m = m+ tags.charAt(i);
			}
		}
		
		BasicDBObject multipleTagsQuery = new BasicDBObject("tags", new BasicDBObject("$all", tagArr));  		
		DBCursor cursor = coll.find(multipleTagsQuery); 		
		while(cursor.hasNext()) { 			
			Document a = new Document();
		    BasicDBObject obj = (BasicDBObject) cursor.next();
		    a.setId(obj.getInt("id"));
		    a.setName(obj.getString("name"));
		    a.setScore((float)obj.getInt("score"));
		    a.setText(obj.getString("text"));
		    a.setLinks((ArrayList<String>)obj.get("links"));
		    a.setTags((ArrayList<String>)obj.get("tags"));
		    documents.add(a);			
		} 		
		return documents;
	}
	public void update(Integer id, Float score, String name, String text,
			ArrayList<String> tags, ArrayList<String> links){			
			BasicDBObject query = new BasicDBObject("id", id);
			BasicDBObject updateObj = new BasicDBObject();			
			updateObj.put("id", id);
			updateObj.put("score", score);
			updateObj.put("name", name);
			updateObj.put("text", text);
			updateObj.put("tags", tags);
			updateObj.put("links", links);
			
			System.out.println("Updating..");
			
			coll.update(query, updateObj);		
	}

	public boolean close(int id) throws IOException{
		if (find(id) != null) {
			BasicDBObject query = new BasicDBObject("id",id);
			//DBCursor cursor = (DBCursor) 
			coll.findAndRemove(query);
			return true;
		}
		else
			return false;
	}
	public void deleteTags(String tags) throws IOException {
		ArrayList<String> tagArr = new ArrayList<String>();
		//tagArr.add(tags);
		String m = "";
		for(int i=0; i<= tags.length()-1; i++){
			if(tags.charAt(i)==':'){
				tagArr.add(m);
				m="";
			}
			else{
				m = m+ tags.charAt(i);
			}
		}
		BasicDBObject multipleTagsQuery = new BasicDBObject("tags", new BasicDBObject("$all", tagArr));  	
		coll.remove(multipleTagsQuery);

	}
}