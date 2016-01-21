import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import exceptions.GoalNotFoundException;
import exceptions.IncoherentDBContentException;
import exceptions.NotReadableElementException;
import exceptions.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.InvalidParameterException;

public class ReadService {
	private static DocumentBDDConnector bdd = MongoDBConnector.getInstance();
	final Logger logger = LogManager.getLogger(ReadService.class);

	@GET
	@Path("/badges/")
	@Produces("application/json")
	public Response getBadges(@QueryParam("userID") String userID) {
		try {
			if (userID != null && !userID.isEmpty() && !userID.equalsIgnoreCase("undefined")) {
				return Response.ok().entity(Model.getInstance().getBadgesOfUser(userID).toString()).build();
			} else {
				// TODO: 06/12/2015 implement
				JsonArray result = new JsonArray();
				return Response.ok().entity(result.toString()).build();
			}
		} catch (IOException e) {
			System.out.println("\n" + e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/challenges")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllChallenges(@QueryParam("type") String typeOfChallenges, @QueryParam("userID") String userID) {
		try {
			//	If user field is set
			if (userID != null && !userID.isEmpty()) {

				//	Check if user exists
				// TODO: 05/12/2015

				switch (typeOfChallenges) {
					case "taken":
						return Response.ok().entity(Controller.getInstance().getTakenChallengesOfUser(userID).toString()).build();
					case "notTaken":
						return Response.ok().entity(Controller.getInstance().getNotTakenChallengesOfUser(userID).toString()).build();
					default:
						return Response.status(403).entity("Type : " + typeOfChallenges + " not recognized.").build();
				}
			} else {
				return Response.ok().entity(Controller.getInstance().getAllChallenges().toString()).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.catching(e);
			return Response.status(500).entity(e.getMessage()).build();
		} catch (IncoherentDBContentException e) {
			logger.catching(e);
			e.printStackTrace();
			return Response.status(500).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/goals/")
	@Produces("application/json")
	public Response getAllGoals(@QueryParam("userID") String userID) {
		if (userID != null && !userID.isEmpty() && !userID.equalsIgnoreCase("undefined")) {
			JsonArray goalResultArray = bdd.findGoalsOfUser(userID);
			return Response.ok().entity(goalResultArray).build();
		} else {
			return Response.status(403).build();
		}

	}

	@GET
	@Path("/goals/{id}")
	@Produces("application/json")
	public Response getGoalById(@PathParam("id") String goalID) {

		try {
			JsonObject result = bdd.findGoalResult(goalID);

			if (result == null) {
				throw new GoalNotFoundException(goalID);
			}

			return Response.ok().entity(result.toString()).build();

		} catch (GoalNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).build();
		}
	}


	@GET
	@Path("/users/{id}/profile")
	public Response getUser(@PathParam("id") String id) {
		try {
			JsonObject user = bdd.findUser(id); // TODO: 20/01/2016 transform data for client

			if (user == null) {
				throw new UserNotFoundException(id);
			}

			return Response.ok().entity(user).build();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).build();
		}
	}

	@GET
	@Path("/users")
	@Produces("application/json")
	public Response getAllUsers() {
		try {
			JsonArray usersJsonArray = bdd.findAllUsers();
			return Response.ok().entity(usersJsonArray).build();
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		}
	}
}
