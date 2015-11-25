package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.controller.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.InvalidParameterException;

@Path("/test")
public class TestService {
	//	TODO delete this token : vertical test only
	@Path("/conditionTest")
	@POST
	@Consumes("application/json")
	public Response buildCondition(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();

		try {
			Controller.getInstance().createCondition(json);
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}

		return Response.ok().entity(json.toString()).build();
	}

	@Path("/expressionTest")
	@POST
	@Consumes("application/json")
	public Response buildExpression(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		try {
			Controller.getInstance().createExpression(json);
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().entity(json.toString()).build();
	}

	@Path("/levelTest")
	@POST
	@Consumes("application/json")
	public Response buildLevel(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		try {
			Controller.getInstance().createLevel(json);
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().entity(json.toString()).build();
	}
}
