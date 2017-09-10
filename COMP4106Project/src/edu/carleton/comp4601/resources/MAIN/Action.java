package edu.carleton.comp4601.resources.MAIN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.sun.jersey.api.view.Viewable;

import edu.carleton.comp4601.dao.Document;
import edu.carleton.comp4601.dao.DocumentCollection;
import edu.carleton.comp4601.dao.Movie;
import edu.carleton.comp4601.dao.MovieCollection;
import edu.carleton.comp4601.dao.User;

public class Action {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	String id;

	public Action(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	@GET
	//@Path("document")
	@Produces(MediaType.TEXT_HTML)
	public String getRestaurantHTML() throws NumberFormatException, IOException {
		Movie a = MovieCollection.getInstance().findMovie(new Integer(id));
		String result=  "<html> " + "<title> Movies </title>" + 
				"<body>"+
				"<h1>" +"id: "+ a.getId() + "</h1>"+ 
				"<h1>" +"name: "+ a.getName() + "</h1>"+ " "+
				"<a href='http://localhost:8080/MoviE/"  + "User.html" +  "'>" +  "Add A User Rating" +
				"</a>";
		for(Object u: a.getusers()){
			//u.toString();
			User user = new User();
			user.covert(u);

			result+="<h2>"  + user.getName()+ " : "+user.getRatings()  +"</h2>";
			//result+="<h2>" + "rating:" + user.getRatings() +"</h2>";
			//u.c
		}
		//"<h1>" +"users: "+ a.getusers() + "</h1>";	
		result+="<h1> </h1>"+ 
				"</body>" +
				"</html> ";

		return result;
	}
//	@GET
//	@Path("Review.html")
//	@Produces(MediaType.TEXT_HTML)
//	public Viewable displayForm() {
//	   return new Viewable("/Review.html");
//	}
//	@GET
//	@Path("Review.html")
//	@Produces(MediaType.TEXT_HTML)
//	public String revHTML() {
//		System.out.println("im reviewing");
//		return id;
//		
//	}
	
//	@POST
//	@Path("review")
//	@Produces(MediaType.TEXT_HTML)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response newReview(@FormParam("id") String revid,
//			@FormParam("name") String name,
//			@FormParam("rating") String rating,//cat
//			@FormParam("description") String description,//tags
//			@Context HttpServletResponse servletResponse) throws IOException {
//		Response res = null;
//		Integer newId = new Integer(revid).intValue();
//		Integer newresId = new Integer(id).intValue();
//		Integer newrating = new Integer(rating).intValue();
//		String newText = description;
//		if (newText == null)
//			newText = "";
//		String newName = name;
//		if (newName == null)
//			newName = "";
//		
//		
//		//get a date
//		
//		if(RestaurantCollection.getInstance().addReview(newId, newresId, newName, 0, newrating, newText)){
//			
//			res= Response.ok().build();
//		}
//		servletResponse.sendRedirect("../Review.html");
//		return res;
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	public String getDocumentXML() throws NumberFormatException, IOException {
//		Document a = DocumentCollection.getInstance().findDocument(new Integer(id));
//		return "<?xml version=\"1.0\"?>" +
//				"<sda> " + a.getName() + " </sda>"+
//				"<sda> " +a.getId() + " </sda>"+
//				"<sda> " +a.getName() + " </sda>"+
//		   		"<sda> " +a.getScore() + " </sda>"+
//		   		"<sda> " +a.getText() + " </sda>"+
//		   		"<sda> " +a.getLinks()+ " </sda>"+
//		   		"<sda> " +a.getTags() + " </sda>";
//	}
	
//	@DELETE
//	public void deleteDocument() throws NumberFormatException, IOException {
//		if (!DocumentCollection.getInstance().close(new Integer(id)))
//			throw new RuntimeException("Document " + id + " not found");
//	}

//	@PUT
//	@Consumes(MediaType.APPLICATION_XML)
//	public Response putDocument(JAXBElement<Restaurant> doc) throws IOException {
//		Restaurant c = doc.getValue();
//		return putAndGetResponse(c);
//	}
//	
//	private Response putAndGetResponse(Restaurant doc) throws IOException {
//		Response res;
//		if (RestaurantCollection.getInstance().containsKey(doc)) {
//			res = Response.ok().build();
//			RestaurantCollection.getInstance().update(doc.getId(), doc.getScore(),doc.getName(),doc.getText(),
//					doc.getTags(),doc.getLinks());
//		} else {
//			res = Response.created(uriInfo.getAbsolutePath()).build();
//			RestaurantCollection.getInstance().addRestaurant(id, name, description, image, cat, tags)open(doc.getId(), doc.getScore(),doc.getName(),doc.getText(),
//					doc.getTags(),doc.getLinks());
//		}
////		if (DocumentCollection.getInstance().containsKey(doc)) {
////			res = Response.ok().build();
////			DocumentCollection.getInstance().update(doc.getId(), doc.getScore(),doc.getName(),doc.getText(),
////					doc.getTags(),doc.getLinks());
////		} else {
////			res = Response.created(uriInfo.getAbsolutePath()).build();
////			DocumentCollection.getInstance().open(doc.getId(), doc.getScore(),doc.getName(),doc.getText(),
////					doc.getTags(),doc.getLinks());
////		}
//		return res;
//	}
}
