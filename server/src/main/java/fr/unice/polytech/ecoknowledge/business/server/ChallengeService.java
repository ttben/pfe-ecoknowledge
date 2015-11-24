package fr.unice.polytech.ecoknowledge.business.server;

import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/challenge")
public class ChallengeService {

	private String name;

	public ChallengeService(String name) {
		this.name = name;
	}

	@POST
	@Consumes("application/json")
	public Response addChallenge(String object) {
		JSONObject jsonObject = new JSONObject(object);



		return Response.ok().entity(jsonObject.toString() + this.name).build();
	}
}
