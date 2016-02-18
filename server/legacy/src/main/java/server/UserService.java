package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.InvalidParameterException;

@Path("/users")
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
			return Response.status(403).entity(e.getStackTrace()).build();
		} catch (NotSavableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().entity(response.toString()).build();
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
