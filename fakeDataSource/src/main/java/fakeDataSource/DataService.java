package fakeDataSource;

import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
public class DataService {

	@GET
	public Response getTest() {
		JsonObject fakeJsonObject = new JsonObject();
		fakeJsonObject.addProperty("testOk", true);
		return Response.ok().entity(fakeJsonObject.toString()).build();
	}

	@GET
	@Path("/sensors/{sensorName}/data")
	public Response getData(@PathParam("sensorName")String sensorName, @QueryParam("date") String date) {
		JsonObject fakeJsonObject = new JsonObject();
		fakeJsonObject.addProperty("testOk", true);
		return Response.ok().entity(fakeJsonObject.toString()).build();
	}
}