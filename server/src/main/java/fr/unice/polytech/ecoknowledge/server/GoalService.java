package fr.unice.polytech.ecoknowledge.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.controller.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/goal")
public class GoalService {

	@POST
	@Consumes("application/json")
	public Response takeChallenge(String payload) {
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();

		try {
			Controller.getInstance().createGoal(jsonObject);
			return Response.ok().build();
		}
		catch(JsonMappingException | JsonParseException e) {
			return Response.status(403).entity(e.getMessage()).build();
		}
		catch (IOException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

}
