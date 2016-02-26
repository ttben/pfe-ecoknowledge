package fr.unice.polytech.ecoknowledge.domain;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.SensorNeeds;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TrackingRequestSender {
	public static Response POST(SensorNeeds sensorNeeds) {
		return POST("http://localhost:8081/webTierFeeder", "/", sensorNeeds);
	}

	private static Response POST(String ipAddress, String service, SensorNeeds sensorNeeds) {
		JsonObject trackingRequest = new JsonObject();
		trackingRequest.addProperty("targetSensor", sensorNeeds.getTargetSensor());
		trackingRequest.addProperty("dateStart", sensorNeeds.getDateStart());
		trackingRequest.addProperty("dateEnd", sensorNeeds.getDateEnd());
		trackingRequest.addProperty("frequency", 100);

		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ipAddress + service);
		Invocation.Builder b = resource.request();
		System.out.println("\t---> Sending request to '" + ipAddress + service + "'");

		Entity e = Entity.entity(trackingRequest.toString(), MediaType.APPLICATION_JSON);

		return b.post(e);
	}
}