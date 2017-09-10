package edu.carleton.comp4601.resources.MAIN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import edu.carleton.comp4601.dao.Document;
import edu.carleton.comp4601.dao.DocumentCollection;
//@Path("/document")
public class NewAction {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	String id;

	public NewAction(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
//	@GET
//	@Path("/document/")
//	//@Produces(MediaType.APPLICATION_XML)
//	public Document deleteDocumentHTML() throws NumberFormatException, IOException {
//		Document a = DocumentCollection.getInstance().deleteTags(id);
//		if (a == null) {
//			throw new RuntimeException("No such document: " + id);
//		}
//		return a;
//	}
//	//@Path("{id}")
//	@DELETE
//	public void deleteDocument() throws NumberFormatException, IOException {
//		if (!DocumentCollection.getInstance().close(new Integer(id)))
//			throw new RuntimeException("Document " + id + " not found");
//	}
}
