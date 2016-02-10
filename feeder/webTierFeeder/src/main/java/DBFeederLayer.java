import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import core.MongoDBConnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/01/2016.
 */
public class DBFeederLayer {
	private static MongoDBConnector dbConnector = MongoDBConnector.getInstance();

	public static List<TrackRequestPOJO> getAllTrackingRequests() throws IOException {
		List<TrackRequestPOJO> result = new ArrayList<>();

		JsonArray trackingRequestsJsonArray = dbConnector.findAllTrackingRequest();

		for(JsonElement currentTrackingRequestElement : trackingRequestsJsonArray) {
			JsonObject currentTrackingRequestJsonObject = currentTrackingRequestElement.getAsJsonObject();
			ObjectMapper mapper = new ObjectMapper();
			TrackRequestPOJO currentTrackingRequest = mapper.readValue(currentTrackingRequestJsonObject.toString(), TrackRequestPOJO.class);
			result.add(currentTrackingRequest);
		}

		return result;
	}
}
