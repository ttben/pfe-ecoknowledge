package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.utils.Utils;
import fr.unice.polytech.ecoknowledge.controller.Controller;
import org.json.JSONObject;

import javax.ws.rs.*;
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

	@Path("/evaluate")
	@POST
	@Consumes("application/json")
	public Response evaluate(String object){
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		try{
			Controller.getInstance().evaluate(
					json.get("userId").getAsString(),
					json.get("challengeId").getAsString());
		} catch (IOException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().build();
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

	@Path("/challengeTest")
	@POST
	@Consumes("application/json")
	public Response buildChallenge(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		try {
			Controller.getInstance().createChallenge(json);
		} catch (InvalidParameterException e) {
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().entity(json.toString()).build();
	}

	@Path("/db/names")
	@GET
	public Response getDBNames() {
		return Response.ok().entity(new Utils().displayTableNames()).build();
	}

	@Path("/db/content")
	@GET
	public Response getDBContent() {
		return Response.ok().entity(new Utils().displayAllContentInStr()).build();
	}

	@Path("/db/")
	@POST
	public Response addDB(String obj) {
		JsonObject object = new JsonParser().parse(obj).getAsJsonObject();
		String name = object.get("name").getAsString();
		return Response.ok().entity(new Utils().createTable(name)).build();
	}
}
