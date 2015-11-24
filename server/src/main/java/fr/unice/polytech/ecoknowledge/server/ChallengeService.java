package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.controller.Controller;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/challenge")
public class ChallengeService {

	@POST
	@Consumes("application/json")
	public Response addChallenge(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		Controller.getInstance().createChallenge(json);
		return Response.ok().entity(json.toString()).build();
	}
}
