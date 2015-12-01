package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.controller.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/users")
public class UserService {

	@POST
	public Response registerUser(String payload) {
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
		try {
			JsonObject result = Controller.getInstance().createUser(jsonObject);
			return Response.ok().entity(result.toString()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
