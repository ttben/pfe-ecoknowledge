package server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Sébastien on 22/11/2015.
 */

@Path("/example")
public class MyExampleService {

	@GET
	public Response getResponse() {
		return Response.ok().build();
	}

}
