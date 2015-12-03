package fr.unice.polytech.ecoknowledge.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/goal")
public class GoalService {

	@POST
	@Consumes("application/json")
	public Response takeChallenge(String payload) {
		System.out.printf("POST : TAKE CHALLENGE : PAYLOAD : \n" + payload);
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();

		try {
			JsonObject result = Controller.getInstance().createGoal(jsonObject);
			return Response.ok().entity(result.toString()).build();
		}
		catch(JsonMappingException | JsonParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(403).entity(e.getMessage()).build();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces("application/json")
	public Response getAllGoals() {
		JsonArray result = Controller.getInstance().getAllGoals();
		return Response.ok().entity(result.toString()).build();
	}

	@DELETE
	public Response deleteAllGoals() {
		Controller.getInstance().dropAllGoals();
		return Response.ok().build();
	}

}
