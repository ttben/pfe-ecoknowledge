package fr.unice.polytech.ecoknowledge.fakeDataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Path("/")
public class DataService {

	@POST
	@Path("fakeData")
	public Response addFakeData(String payload) throws IOException, URISyntaxException {

		JsonObject newFakeData = new JsonParser().parse(payload).getAsJsonObject();

		URL url = Thread.currentThread().getContextClassLoader().getResource("fakeData.json");

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));


		String sCurrentLine = "";
		String jsonDesc = "";
		while ((sCurrentLine = bufferedReader.readLine()) != null) {
			jsonDesc = jsonDesc.concat(sCurrentLine);
		}

		JsonObject completeSetOfPreviousFakeData =  new JsonParser()
				.parse(jsonDesc).getAsJsonObject();

		bufferedReader.close();

		String newSensorName = newFakeData.get("targetSensor").getAsString();
		JsonObject newDateAndValuePair = newFakeData.get("value").getAsJsonObject();


		JsonArray newValuesForSensorTarget = new JsonArray();
		Object oldValuesObj = completeSetOfPreviousFakeData.get(newSensorName);
		if(oldValuesObj != null) {
			newValuesForSensorTarget = completeSetOfPreviousFakeData.get(newSensorName).getAsJsonArray();
		}

		newValuesForSensorTarget.add(newDateAndValuePair);

		completeSetOfPreviousFakeData.remove(newSensorName);
		completeSetOfPreviousFakeData.add(newSensorName, newValuesForSensorTarget);


		File f = new File(url.toURI());
		if(!f.exists()) {
			f.createNewFile();
		}

		FileWriter fw = new FileWriter(f);

		BufferedWriter bufferedWriter = new BufferedWriter(fw);
		bufferedWriter.write(completeSetOfPreviousFakeData.toString());

		bufferedWriter.close();

		return Response.ok().entity(completeSetOfPreviousFakeData.toString()).build();
	}


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