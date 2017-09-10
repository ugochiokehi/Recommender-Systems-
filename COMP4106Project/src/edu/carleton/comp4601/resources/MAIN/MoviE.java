package edu.carleton.comp4601.resources.MAIN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.mongodb.BasicDBObject;

import Jama.Matrix;
import edu.carleton.comp4601.cf.dao.UserBased;
import edu.carleton.comp4601.cf.dao.ItemBased;
import edu.carleton.comp4601.cf.dao.SVD;
import edu.carleton.comp4601.dao.Document;
import edu.carleton.comp4601.dao.DocumentCollection;
import edu.carleton.comp4601.dao.Movie;
import edu.carleton.comp4601.dao.MovieCollection;
import edu.carleton.comp4601.dao.User;


@Path("/moviE")
public class MoviE {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private String name;

	public MoviE() {
		name = "COMP4601 Searchable Document Archive";
		DocumentCollection.getInstance();
		MovieCollection.getInstance();
	}

	@GET
	public String printName() {
		return name;
	}
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXML() {
		return "<?xml version=\"1.0\"?>" + "<sda> " + name + " </sda>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtml() {
		return "<html> " + "<title>" + name + "</title>" + "<body><h1>" + name
				+ "</h1></body>" + "</html> ";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayJSON() {
		return "{" + name + "}";
	}
		
	@GET
	@Path("home")
	@Produces(MediaType.TEXT_HTML)
	public String getMovieHTML() throws IOException {
		List<Movie> loa = new ArrayList<Movie>();
		loa=MovieCollection.getInstance().getRestaurantList();
		String result = "<html>"+"<title> Movie list </title>";
		result+="<h1> Welcome To MoviE! </h1>";
		result += "<a href='http://localhost:8080/MoviE/Movies.html'>" + "Add Movie" + "</a>";

		for(int i =0;i<=loa.size()-1;i++){
			result+= "<body>" +
					"<h1>"+"Movie Name:"+
					"<a href='http://localhost:8080/MoviE/rest/moviE/" +
					loa.get(i).getId()+ "'>" + loa.get(i).getName() + "</a>"+
					"</h1>" +
					"<h1>" + "Ratings:"+ loa.get(i).getRatings() +"</h1>";
			if(loa.get(i).getusers().size()!= 0){
			//	result+="<h1>" + "Users:" + loa.get(i).getusers() +"</h1>";
				for(Object u: loa.get(i).getusers()){
					//u.toString();
					User user = new User();
					user.covert(u);
					
					result+="<h2>"  + user.getName()+ " : "+user.getRatings()  +"</h2>";
					//result+="<h2>" + "rating:" + user.getRatings() +"</h2>";
					//u.c
				}

			}else{
				result+= "no reviews";
			}
		}
		result+="</body>" +
				"</html> ";
		return result;
	}


	@GET
	@Path("predictions")
	@Produces(MediaType.TEXT_HTML)
	public String getReview() throws IOException {
		List<User> loa = new ArrayList<User>();
		loa=MovieCollection.getInstance().getUserList();
		int[][] rat= new int[5][5];
		String result = "<html>"+"<title> Review list </title>";
		for(int i =0;i<=loa.size()-1;i++){
//			 result+= "<body>" + 
//					 "<h1> movie id: " + loa.get(i).getMovieId() +"</h1>"+
//					 "<h1> userid: " + loa.get(i).getId() +"</h1>"+
//					 "<h1> name: " + loa.get(i).getName() +"</h1>"+
//					 "<h1> rating: " + loa.get(i).getRatings() +"</h1>";
			 for (int index = 1; index <= 5; index++) {
				 for (int j = 1; j <= 5; j++) {
					 if(loa.get(i).getId() == index){
						 if(loa.get(i).getMovieId() == j){
							 rat[index-1][j-1]=loa.get(i).getRatings();
							//System.out.println("the rat: "+loa.get(i).getRatings()+" i:"+index+" j: "+j);
						 }
					 }
				 }
			 }
			
		}
		UserBased cf = new UserBased(rat);
		result+= "<h1> User Based Filtering</h1>";
		result+= "<h2> the prediction for Alice's rating on Hidden Figures is: " + cf.run()  +"</h2>";
		
		ItemBased ib = new ItemBased(rat);
		result+= "<h1> Item Based Filtering</h1>";
		result+= "<h2> the prediction for Alice's rating on Hidden Figures is: " + ib.run()  +"</h2>";
		
		SVD svd = new SVD(rat);
		result+= "<h1> SVD Filtering</h1>";
		result+= "<h2> the prediction for Alice's rating on Hidden Figures is: " + svd.run()  +"</h2>";
		
		
		result+="</body>" +
				"</html> ";
		return result;
	}
	@GET
	@Path("matrix")
	@Produces(MediaType.TEXT_HTML)
	public String getMatrix() throws IOException {
		List<User> loa = new ArrayList<User>();
		loa=MovieCollection.getInstance().getUserList();
		String result = "<html>"+"<title> Review list </title>";
		for(int i =0;i<=loa.size()-1;i++){
			 result+= "<body>" + 
					 "<h1>" + loa.get(i).getMovieId() +"</h1>"+
					 "<h1>" + loa.get(i).getName() +"</h1>"+
					 "<h1>" + loa.get(i).getRatings() +"</h1>";				 
		}
		result+="</body>" +
				"</html> ";
		return result;
	}
	@POST
	@Path("user")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newUser(@FormParam("movieid") String movieid, @FormParam("userid") String userid,
			@FormParam("name") String name,
			@FormParam("rating") String rating,
			@Context HttpServletResponse servletResponse) throws IOException {
		System.out.println("The review in sda");
		Response res = null;
		Integer newId = new Integer(userid).intValue();
		Integer newmovieId = new Integer(movieid).intValue();
		Integer newrating = new Integer(rating).intValue();
		String newName = name;
		if (newName == null)
			newName = "";
		Map<Object, Object> d = new ConcurrentHashMap<>();
		d.put("id", newId);
		d.put("movieid", newmovieId);
		d.put("name", newName);
		d.put("rating",newrating);
	
				
		User user = new User(d);	
		System.out.println("in the sda userid: "+user.getId()+" movieid: "+user.getMovieId()+
				" name: "+user.getName()+" rating: "+user.getRatings());
		if(MovieCollection.getInstance().run(user)){
			res= Response.ok().build();
		}
		//res= Response.ok().build();
		servletResponse.sendRedirect("../moviE/home");
		return res;
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newMovie(@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("description") String text,
			@Context HttpServletResponse servletResponse) throws IOException {
		Response res = null;
		String newText = text;
		if (newText == null)
			newText = "";
		String newName = name;
		if (newName == null)
			newName = "";
		Integer newId = new Integer(id).intValue();
		
		if(MovieCollection.getInstance().addMovie(newId, newName, newText)){
			
			res= Response.ok().build();
		}
		servletResponse.sendRedirect("../rest/moviE/home");
		return res;
	}

	@Path("{id}")
	public Action getDocument(@PathParam("id") String id) {
		return new Action(uriInfo, request, id);
	}
}
