package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.regexp.internal.RE;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.data.core.Utils;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {

	@POST
	@Path("/clock")
	public Response setFakeTime(String payload) {
		JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();

		long newTime = jsonObject.get("newTime").getAsLong();
		Clock.getClock().setFakeTime(Clock.getClock().createDate(new DateTime(newTime)));
		return Response.ok().entity(Clock.getClock().getTime().getMillis()/1000).build();
	}

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
