package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.controller.Controller;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/challenge")
public class ChallengeService {

	@POST
	@Consumes("application/json")
	public Response addChallenge(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		JsonObject result = null;
		try {
			result = Controller.getInstance().createChallenge(json);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().entity(result.toString()).build();
	}


	@GET
	@Path("/${challengeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBadge(@PathParam("challengeId") String challengeId){

		JSONObject response = Controller.getInstance().searchBadge(challengeId);

		if(response.getBoolean("valid"))
			return  Response.ok(response.toString()).build();
		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllChallenges() {
		return Response.ok().entity(Controller.getInstance().getAllChallenges().toString()).build();
	}
}
