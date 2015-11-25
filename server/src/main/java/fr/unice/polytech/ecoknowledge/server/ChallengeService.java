package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.controller.Controller;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/challenge")
public class ChallengeService {

	@POST
	@Consumes("application/json")
	public Response addChallenge(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		JsonObject id = Controller.getInstance().createChallenge(json);
		return Response.ok().entity(id.toString()).build();
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
}
