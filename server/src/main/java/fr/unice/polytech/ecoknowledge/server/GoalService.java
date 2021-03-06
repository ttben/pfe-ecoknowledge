package fr.unice.polytech.ecoknowledge.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.data.exceptions.*;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.InvalidGoalTimespanOverChallengeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/goals")
public class GoalService {

	final Logger logger = LogManager.getLogger(GoalService.class);

	@POST
	@Consumes("application/json")
	public Response takeChallenge(String payload) {
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();

		logger.info("POST goal service with payload : ");
		logger.info(jsonObject.toString());

		if(!jsonObject.has("challenge")) {
			return Response.status(400).entity("'challenge' field not specified").build();
		}

		if(!jsonObject.has("user")) {
			return Response.status(400).entity("'user' field not specified").build();
		}

		try {
			JsonObject result = Controller.getInstance().takeChallenge(jsonObject);
			return Response.ok().entity(result.toString())
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.header("Access-Control-Max-Age", "1209600")
					.build();
		} catch (JsonMappingException | JsonParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		} catch (GoalNotFoundException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (NotSavableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (InvalidGoalTimespanOverChallengeException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces("application/json")
	@Deprecated
	public Response getAllGoals(@QueryParam("userID") String userID) {

		try {
			if (userID != null && !userID.isEmpty() && !userID.equalsIgnoreCase("undefined")) {
				System.out.println("User ID specified (" + userID + "). Displaying goals for user ...");
				return Response.ok().entity(Controller.getInstance().getGoalsResultOfUser(userID).toString()).build();
			} else {
				JsonArray result = new JsonParser().parse(MongoDBConnector.getInstance().findAllGoals().toString()).getAsJsonArray();
				return Response.ok().entity(result.toString()).build();
				// return Response.status(403).build();
			}
		} catch (GoalNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).build();
		} catch (IncoherentDBContentException e) {
			e.printStackTrace();
			return Response.status(500).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	@Deprecated
	public Response getGoalById(@PathParam("id") String goalID) {

		try {
			JsonObject result = Controller.getInstance().getGoalResult(goalID);
			return Response.ok().entity(result.toString()).build();

		} catch (GoalNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
