package fr.unice.polytech.ecoknowledge.server;

import fr.unice.polytech.ecoknowledge.domain.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/mosaic")
public class MosaicService {
	@GET
	public Response getMosaic(@QueryParam("userID") String userID) {
		try {
			System.out.println("USER ID : " + userID);
			//	If user not logged
			//	TODO check if userID exists
			//	TODO move this code into controller
			if (userID != null && !userID.isEmpty() && !userID.equalsIgnoreCase("undefined")) {
				System.out.println("User ID specified (" + userID + "). Displaying goals for user ...");

				return Response.ok().entity(Controller.getInstance().getMosaicForUser(userID).toString()).build();
			} else {
				System.out.println("User ID not specified. Displaying challenges ...");
				return Response.ok().entity(Controller.getInstance().getAllChallenges().toString()).build();
			}
		} catch (IOException e) {
			System.out.println("\n" + e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
