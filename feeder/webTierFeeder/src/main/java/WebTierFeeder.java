import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import core.MongoDBConnector;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/")
public class WebTierFeeder {
	private static MongoDBConnector bdd = MongoDBConnector.getInstance();
	final Logger logger = LogManager.getLogger(WebTierFeeder.class);

	@POST
	@Path("init")
	public Response init() {
		try {
			App.main(null);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addNewTrackingRequest(String trackingRequestDescription) throws IOException {
		JsonObject trackingRequestJsonDescription = new JsonParser().parse(trackingRequestDescription).getAsJsonObject();

		TrackRequestPOJO newTrackingRequest = new ObjectMapper().readValue(trackingRequestDescription.toString(), TrackRequestPOJO.class);
		List<TrackRequestPOJO> trackRequestPOJOList = DBFeederLayer.getAllTrackingRequests();

		for(TrackRequestPOJO currentTrackingRequest : trackRequestPOJOList) {

			//	Check if sensor is already tracked
			if(currentTrackingRequest.hasSameTargetThan(newTrackingRequest)) {
				System.out.println("New tracking request collide with previous tracking request");
				long newStartDate, newEndDate;

				System.out.println("startdate?" + (newTrackingRequest.getDateStart() < currentTrackingRequest.getDateStart()));
				System.out.println("enddate?" + (newTrackingRequest.getDateEnd() > currentTrackingRequest.getDateEnd()));

				newStartDate = (newTrackingRequest.getDateStart() < currentTrackingRequest.getDateStart()) ? newTrackingRequest.getDateStart() : currentTrackingRequest.getDateStart();
				newEndDate = (newTrackingRequest.getDateEnd() > currentTrackingRequest.getDateEnd()) ? newTrackingRequest.getDateEnd() : currentTrackingRequest.getDateEnd();

				System.out.println("OLD TRACKING REQUEST " + currentTrackingRequest);

				currentTrackingRequest.setDateEnd(newEndDate);
				currentTrackingRequest.setDateStart(newStartDate);
				System.out.println("NEW TRACKING REQUEST " + currentTrackingRequest);

				JsonObject newTrackingRequestJsonObject = new JsonParser().parse(new ObjectMapper().writeValueAsString(currentTrackingRequest)).getAsJsonObject();
				bdd.updateTrackingRequest(newTrackingRequestJsonObject);

				return Response.ok().build();
			}
		}

		// System.out.println(trackingRequestJsonDescription);
		JsonObject newTrackingRequestJsonObject = new JsonParser().parse(new ObjectMapper().writeValueAsString(newTrackingRequest)).getAsJsonObject();
		bdd.storeTrackingRequest(newTrackingRequestJsonObject);

		return Response.ok().build();
	}

	@GET
	public Response getAllTrackingRequests() {
		return Response.ok().entity(bdd.findAllTrackingRequest().toString()).build();
	}

	@DELETE
	public Response deleteAllTrackingRequests() {
		bdd.deleteAllTrackingRequests();
		return Response.ok().build();
	}
} 
