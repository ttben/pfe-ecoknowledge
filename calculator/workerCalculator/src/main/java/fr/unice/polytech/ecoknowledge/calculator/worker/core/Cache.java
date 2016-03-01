package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.SensorExtractor;
import fr.unice.polytech.ecoknowledge.domain.model.SensorNeeds;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class Cache {

	private final Logger logger = LogManager.getLogger(Cache.class);

	private static Cache fakeCacheInstance;
	private Map<String, List<Data>> data = new HashMap<>();

	public Cache(Goal goal) {

		logger.debug("Building cache for goal " + goal.getId());
		SensorExtractor sensorExtractor = new SensorExtractor(goal);

		logger.debug("Extracting info from goal");
		goal.accept(sensorExtractor);

		List<SensorNeeds> listOfSensorNeeds = sensorExtractor.getSensorNeedsList();

		logger.debug("List of sensor needed for goal " + goal.getId() + " : " + listOfSensorNeeds);

		for(SensorNeeds sensorNeeds : listOfSensorNeeds) {
			List<Data> currentData = getDataOf(sensorNeeds);
			data.put(sensorNeeds.getTargetSensor(), currentData);
		}

		logger.debug("Info extraction ended");
	}

	// TODO: 24/02/2016 ADD FILTER FOR DAY TIMES
	public List<Data> getDataOfSensorBetweenDates(String sensorName,
												  DateTime start, DateTime end) {

		List<Data> result = new ArrayList<>();

		List<Data> listOfDataForGivenSensor = this.getData().get(sensorName);
		logger.debug("Getting data of sensor " + sensorName + ". Current data : " + getData());
		if(listOfDataForGivenSensor.size() == 0) {
			return result;
		}

		for(Data currentData : listOfDataForGivenSensor) {
			if(currentData.getDate().isAfter(start) && currentData.getDate().isBefore(end)) {
				result.add(currentData);
			}
		}

		logger.debug("Returning data for " + sensorName + " : " + result);

		return result;
	}

	public List<Data> getDataOf(SensorNeeds sensorNeeds) {
		DateTime start = new DateTime(sensorNeeds.getDateStart());
		DateTime end = new DateTime(sensorNeeds.getDateEnd());
		String sensorName = sensorNeeds.getTargetSensor();

		//System.out.println("SENSOR NAME ASKED : " + sensorName);

		return readDataOfSensorBetweenDateFromDB(sensorName, start, end);
	}

	public List<Data> readDataOfSensorBetweenDateFromDB(String sensorName,
														 DateTime start, DateTime end) {

		JsonObject sensorData = MongoDBHandler.getInstance().getBddConnector().getSensorDataBetweenDates(sensorName, start.getMillis(), end.getMillis());
		JsonArray sensorDataValues = sensorData.getAsJsonArray("values");

		List<Data> result = new ArrayList<>();

		for(JsonElement sensorDataValuesElement : sensorDataValues) {
			JsonObject sensorDataValuesObject = sensorDataValuesElement.getAsJsonObject();
			double value = sensorDataValuesObject.get("value").getAsDouble();
			String date = sensorDataValuesObject.get("date").getAsLong() + "";

			Long dateLong = Long.parseLong(date);
			dateLong = dateLong * 1000; 	//	Convert epoch time in seconds into epoch time in millis

			date = "" + dateLong;

			DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss'Z'");
			DateTime dateTimeInClockRepresentation = new DateTime(dateLong);
			String dateStrForClockAPI = dateTimeInClockRepresentation.toString(dtf);

			Data currentData = new Data(value, Clock.getClock().parseDate(dateStrForClockAPI));
			result.add(currentData);
		}

		return result;

	}

	@Deprecated
	public List<Data> getDataOfSensorBetweenDate(String sensorName, DateTime start, DateTime end,
												 AbstractMap.SimpleEntry<Integer, Integer> weekMoment,
												 List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment) {
		List<Data> data = readDataOfSensorBetweenDateFromDB(sensorName, start, end);
		return new Filter(weekMoment, dayMoment).filter(data);
	}

	public Map<String, List<Data>> getData() {
		return data;
	}

	public void setData(Map<String, List<Data>> data) {
		this.data = data;
	}

	public void addData(JsonObject object) {
		String sensorName = object.get("sensor").getAsString();
		String dataDescription = object.get("data").getAsString();
		String dateDescription = object.get("date").getAsString();

		Double dataValue = Double.parseDouble(dataDescription);
		DateTime dateTime = DateTime.parse(dateDescription);

		Data dataToAdd = new Data(dataValue, dateTime);

		List<Data> dataList = new ArrayList<>();
		if (fakeCacheInstance.getData().containsKey(sensorName)) {
			dataList = fakeCacheInstance.getData().get(sensorName);
		}
		dataList.add(dataToAdd);

		fakeCacheInstance.getData().put(sensorName, dataList);
	}
}
