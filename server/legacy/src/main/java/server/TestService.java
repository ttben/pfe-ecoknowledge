package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.data.core.Utils;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {

	@GET
	@Path("/db/names")
	public Response getDBNames() {
		return Response.ok().entity(new Utils().displayTableNames()).build();
	}

	@GET
	@Path("/db/content")
	public Response getDBContent() {
		return Response.ok().entity(new Utils().displayAllContentInStr()).build();
	}

	@POST
	@Path("/db/")
	public Response addDB(String obj) {
		JsonObject object = new JsonParser().parse(obj).getAsJsonObject();
		String name = object.get("name").getAsString();
		return Response.ok().entity(new Utils().createTable(name)).build();
	}


	@DELETE
	@Path("/db/{dbName}")
	public Response dropDB(@PathParam("dbName") String dbName) {
		final Logger logger = org.apache.logging.log4j.LogManager.getLogger(MongoDBConnector.class);
		logger.warn("Ask to drop DB " + dbName);

		System.out.println("\n\n DROP THE DB " + dbName);

		Controller.getInstance().drop(dbName);
		return Response.ok().build();
	}

}
