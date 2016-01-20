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

		try {
			if (userID != null && !userID.isEmpty() && !userID.equalsIgnoreCase("undefined")) {
				System.out.println("User ID specified (" + userID + "). Displaying goals for user ...");
				return Response.ok().entity(Controller.getInstance().getGoalsResultOfUser(userID).toString()).build();
			} else {
				return Response.status(403).build();
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
	@Path("/goals/{id}")
	@Produces("application/json")
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


	@GET
	@Path("/users/{id}/profile")
	public Response getUser(@PathParam("id") String id) {
		try {
			return Response.ok().entity(Controller.getInstance().getUserProfile(id).toString()).build();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/users")
	public Response getAllUsers() {
		try {
			return Response.ok().entity(Controller.getInstance().getAllUserProfiles().toString()).build();
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (IncoherentDBContentException e) {
			e.printStackTrace();
			return Response.status(500).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
