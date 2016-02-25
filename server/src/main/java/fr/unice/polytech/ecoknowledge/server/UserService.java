package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.exceptions.*;
import fr.unice.polytech.ecoknowledge.domain.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.InvalidParameterException;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	@POST
	public Response registerUser(String payload) {
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
		JsonObject response = new JsonObject();
		try {
			String id = Controller.getInstance().registerUser(jsonObject);
			response.addProperty("id", id);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getStackTrace())
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
					.build();
		} catch (NotSavableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage())
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
					.build();
		}
		return Response.ok().entity(response.toString())
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
				.build();
	}

	@GET
	@Path("/{mail}")
	public Response getUserId(@PathParam("mail") String mail, @QueryParam("password") String password){
		try{
			return Response.ok().entity(Controller.getInstance().getUserId(mail, password).toString()).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).build();
		} catch (UserBadPasswordException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{id}/profile")
	@Deprecated
	public Response getUser(@PathParam("id") String id) {
		try {
			return Response.ok().entity(Controller.getInstance().getUserProfile(id).toString()).build();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Deprecated
	public Response getAllUsers() {
		try {
			return Response.ok().entity(Controller.getInstance().getAllUserProfiles().toString()).build();
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (IncoherentDBContentException e) {
			e.printStackTrace();
			return Response.status(500).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
