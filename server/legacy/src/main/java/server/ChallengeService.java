package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.IncoherentDBContentException;
import exceptions.NotReadableElementException;
import exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/challenges")
public class ChallengeService {

    final Logger logger = LogManager.getLogger(ChallengeService.class);

	@POST
	@Consumes("application/json")
	public Response addChallenge(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();

		try {
			Controller.getInstance().createChallenge(json);
			return Response.ok().build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (NotSavableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Deprecated
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
}
