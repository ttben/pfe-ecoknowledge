package fr.unice.polytech.ecoknowledge.fakeDataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
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

		//	Convert date in millis into date in seconds
		long dateInMillis = newDateAndValuePair.get("date").getAsLong();
		newDateAndValuePair.remove("date");
		newDateAndValuePair.addProperty("date", dateInMillis/1000);

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

		JsonObject data = new JsonParser()
				.parse(bufferedReader).getAsJsonObject();

		if (!data.has(sensorName)) {
			return Response.status(404).entity("Sensor " + sensorName + " not found").build();
		}

		String[] dateArray = null;
		if(date != null && !date.isEmpty()) {
			date.split("/");
		}

		String startDateStr = null, endDateStr = null;
		Long startDate = null, endDate = null;

		if(dateArray != null && dateArray.length == 1) {
			startDateStr = dateArray[0];
			startDate = Long.parseLong(startDateStr);

		} else if (dateArray != null && dateArray.length >= 2) {
			startDateStr = dateArray[0];
			endDateStr = dateArray[1];
			startDate = Long.parseLong(startDateStr);
			endDate = Long.parseLong(endDateStr);
		}

		JsonArray pairOfDateAndValuesArray = data.getAsJsonArray(sensorName);
		JsonArray resultValues = new JsonArray();
		for(JsonElement currentElement : pairOfDateAndValuesArray) {
			JsonObject currentPairOfDateAndValue = currentElement.getAsJsonObject();
			long currentDate = currentPairOfDateAndValue.get("date").getAsLong();
			if(isKept(currentDate, startDate, endDate)) {
				resultValues.add(currentPairOfDateAndValue);
			}
		}

		JsonObject result = new JsonObject();
		result.addProperty("id", sensorName);
		result.add("values", resultValues);

		return Response.ok().entity(result.toString()).build();
	}

	/**
	 *
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return true if date given is between startDate and endDate
	 */
	private boolean isKept(long date, Long startDate, Long endDate) {
		if (startDate != null) {
			if(endDate != null) {
				return date >= startDate && date <= endDate;
			}
			return date >= startDate;
		}

		return true;
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