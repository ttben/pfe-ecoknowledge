package fr.unice.polytech.ecoknowledge.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.data.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.utils.Utils;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.calculator.Cache;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;
import org.joda.time.DateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/test")
public class TestService {

	@POST
	@Path("/evaluate")
	@Consumes("application/json")
	public Response evaluate(String object) {
		JsonObject json = new JsonParser().parse(object).getAsJsonObject();
		try {
			Model.getInstance().evaluate(
					json.get("userId").getAsString(),
					json.get("challengeId").getAsString());
		} catch (IOException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (GoalNotFoundException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(403).entity(e.getMessage()).build();
		} catch (IncoherentDBContentException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (NotSavableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().build();
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


	@POST
	@Path("/stub")
	@Consumes("application/json")
	public Response addFakeData(String obj) {
		JsonObject object = new JsonParser().parse(obj).getAsJsonObject();
		Cache.getFakeCache().addData(object);
		return Response.ok().entity(Cache.getFakeCache().getData().toString()).build();
	}

	@POST
	@Path("/clock")
	@Consumes("application/json")
	public Response setClock(String obj) {
		JsonObject object = new JsonParser().parse(obj).getAsJsonObject();
		String newDate = object.get("date").getAsString();
		DateTime newDateTime = DateTime.parse(newDate);
		Model.getInstance().setTime(newDateTime);
		return Response.ok().entity(Model.getInstance().getTimeDescription()).build();
	}
}
