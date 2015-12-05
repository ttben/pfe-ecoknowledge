package fr.unice.polytech.ecoknowledge.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/goals")
public class GoalService {

	@POST
	@Consumes("application/json")
	public Response takeChallenge(String payload) {
		System.out.printf("POST : TAKE CHALLENGE : PAYLOAD : \n" + payload);
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();

		try {
			JsonObject result = Model.getInstance().takeChallenge(jsonObject, null);    // TODO: 05/12/2015 clean call: no need to pass "null"
			return Response.ok().entity(result.toString()).build();
		} catch (JsonMappingException | JsonParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces("application/json")
	public Response getAllGoals(@QueryParam("userID") String userID) {

		try {
			System.out.println("USER ID : " + userID);

			if (userID != null && !userID.isEmpty() && !userID.equalsIgnoreCase("undefined")) {
				System.out.println("User ID specified (" + userID + "). Displaying goals for user ...");
				return Response.ok().entity(Model.getInstance().getGoalsOfUserInJsonFormat(userID).toString()).build();
			} else {
				JsonArray result = Model.getInstance().getAllGoals();
				return Response.ok().entity(result.toString()).build();
			}
		} catch (IOException e) {
			System.out.println("\n" + e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getGoalById(@PathParam("id") String userID) {

		try {
			JsonObject result = Model.getInstance().getGoal(userID);
			return Response.ok().entity(result.toString()).build();

		} catch (IOException e) {
			System.out.println("\n" + e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
