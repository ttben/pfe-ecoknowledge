import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class WebTierFeeder {
	private static MongoDBConnector bdd = MongoDBConnector.getInstance();
	final Logger logger = LogManager.getLogger(WebTierFeeder.class);

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addNewTrackingRequest(String trackingRequestDescription) {
		JsonObject trackingRequestJsonDescription = new JsonParser().parse(trackingRequestDescription).getAsJsonObject();

		// System.out.println(trackingRequestJsonDescription);

		bdd.storeTrackingRequest(trackingRequestJsonDescription);

		return Response.ok().build();
	}
} 
