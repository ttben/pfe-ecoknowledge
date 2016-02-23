package server;

import com.google.gson.JsonArray;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/badges")
public class BadgeService {
	@GET
	@Produces("application/json")
	@Deprecated
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
}
