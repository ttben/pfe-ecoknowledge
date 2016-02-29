package fr.unice.polytech.ecoknowledge.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Sébastien on 22/11/2015.
 */

@Path("/")
public class MyExampleService {

	@GET
	public Response getResponse() {
		return Response.ok().entity("c'est ok !").build();
	}

}
