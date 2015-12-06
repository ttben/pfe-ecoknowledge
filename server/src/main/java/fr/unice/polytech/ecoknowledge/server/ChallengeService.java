package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/challenges")
public class ChallengeService {

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
	public Response getAllChallenges(@PathParam("type") String typeOfChallenges, @PathParam("userID") String userID) {
		try {
			//	If user field is set
			if (userID != null && !userID.isEmpty()) {

				//	Check if user exists
				// TODO: 05/12/2015

				switch (typeOfChallenges) {
					case "taken":
						return Response.ok().entity(Controller.getInstance().getTakenChallengesOfUser(userID)).build();
					case "notTaken":
						return Response.ok().entity(Controller.getInstance().getNotTakenChallengesOfUser(userID)).build();
					default:
						return Response.status(403).entity("Type : " + typeOfChallenges + " not recognized.").build();
				}
			} else {
				return Response.ok().entity(Controller.getInstance().getAllChallenges().toString()).build();
			}
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllChallenges() {
		try {
			return Response.ok().entity(Controller.getInstance().getAllChallenges().toString()).build();
		} catch (IncoherentDBContentException e) {
			e.printStackTrace();
			return Response.status(500).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
