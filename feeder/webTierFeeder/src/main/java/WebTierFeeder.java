import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/")
public class WebTierFeeder {
	private static MongoDBConnector bdd = MongoDBConnector.getInstance();
	final Logger logger = LogManager.getLogger(WebTierFeeder.class);

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
				return Response.ok().build();
			}
		}



		// System.out.println(trackingRequestJsonDescription);

		bdd.storeTrackingRequest(trackingRequestJsonDescription);

		return Response.ok().build();
	}

	@DELETE
	public Response deleteAllTrackingRequests() {
		bdd.deleteAllTrackingRequests();
		return Response.ok().build();
	}
} 
