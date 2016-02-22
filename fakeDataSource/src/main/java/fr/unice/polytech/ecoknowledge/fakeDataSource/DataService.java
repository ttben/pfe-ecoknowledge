package fr.unice.polytech.ecoknowledge.fakeDataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

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
	public Response getData(@PathParam("sensorName") String sensorName,
							@QueryParam("date") String date) throws IOException {

		URL url = Thread.currentThread().getContextClassLoader().getResource("fakeData.json");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

		JsonArray oldValues = new JsonParser()
				.parse(bufferedReader).getAsJsonArray();

		JsonArray oldValuesClone = oldValues;

		long dateStart = Long.parseLong(
				(
						(oldValues
								.get(oldValues.size() - 1)
								.getAsJsonObject()
						).get("date").toString()

				).toString()
		);
		long dateEnd = new Date().getTime()/1000;

		System.out.println("DATE START : " + dateStart);
		System.out.println("DATE END : " + dateEnd);

		JsonArray newValues = generateRandomValuesBetweenDateWithSpecificJump(5 * 60, 10, 30, dateStart, dateEnd);

		System.out.println("NEW VALUES \n\n " + newValues);
		System.out.println("OLD VALUES \n\n " + oldValues);

		oldValuesClone.addAll(newValues);


		JsonObject res = new JsonObject();
		res.addProperty("id", sensorName);
		res.add("values", oldValues);

		return Response.ok().entity(res.toString()).build();
	}

	public static void main(String[] args) {
		System.out.println(generateSpecificRandomValuesBetweenDate(2, 10, 20, 1453463517, 1456142312));
		// System.out.println(generateRandomValuesBetweenDateAndBounds(10,20,1453463517,1456142312));
	}

	static JsonArray generateRandomValuesBetweenDateAndBounds(int lowerLimit, int upperLimit, long dateStart, long dateEnd) {
		if (dateStart > dateEnd) {
			throw new IllegalArgumentException("start is after end");
		}

		return generateSpecificRandomValuesBetweenDate(5 * 60, lowerLimit, upperLimit, dateStart, dateEnd);
	}

	private static JsonArray generateRandomValuesBetweenDateWithSpecificJump(long jump, int lowerLimit, int upperLimit, long dateStart, long dateEnd) {
		JsonArray values = new JsonArray();

		long currentTime = dateStart;

		for (long time = currentTime; time <= dateEnd; time = time + (jump)) {
			double randomValue = ThreadLocalRandom.current().nextDouble(lowerLimit, upperLimit);

			JsonObject jsonValue = new JsonObject();
			jsonValue.addProperty("date", time);
			jsonValue.addProperty("value", randomValue);

			values.add(jsonValue);
		}

		return values;
	}

	static JsonArray generateSpecificRandomValuesBetweenDate(int nbOfValueToGenerate, int lowerLimit, int upperLimit, long dateStart, long dateEnd) {
		if (dateStart > dateEnd) {
			throw new IllegalArgumentException("start is after end");
		}

		long jump = (dateEnd - dateStart) / nbOfValueToGenerate;

		return generateRandomValuesBetweenDateWithSpecificJump(jump, lowerLimit, upperLimit, dateStart, dateEnd);
	}
}