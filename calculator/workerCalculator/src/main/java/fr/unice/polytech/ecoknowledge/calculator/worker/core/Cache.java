package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.SensorExtractor;
import fr.unice.polytech.ecoknowledge.domain.model.SensorNeeds;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class Cache {

	private static Cache fakeCacheInstance;
	private Map<String, List<Data>> data = new HashMap<>();

	public Cache(Goal goal) {

		SensorExtractor sensorExtractor = new SensorExtractor(goal);
		goal.accept(sensorExtractor);

		List<SensorNeeds> listOfSensorNeeds = sensorExtractor.getSensorNeedsList();
		for(SensorNeeds sensorNeeds : listOfSensorNeeds) {
			List<Data> currentData = getDataOf(sensorNeeds);

			//System.out.println("Needs :  " + sensorNeeds.getTargetSensor() + " from " + sensorNeeds.getDateStart() + " to " + sensorNeeds.getDateEnd());

			data.put(sensorNeeds.getTargetSensor(), currentData);
		}
	}

	// TODO: 24/02/2016 ADD FILTER FOR DAY TIMES
	public List<Data> getDataOfSensorBetweenDates(String sensorName,
												  DateTime start, DateTime end) {

		List<Data> result = new ArrayList<>();


		List<Data> listOfDataForGivenSensor = this.getData().get(sensorName);
		if(listOfDataForGivenSensor.size() == 0) {
			//System.out.println("THERES NO DATA FOR SPECIFIC SENSOR : " + sensorName);
			return result;
		}

		for(Data currentData : listOfDataForGivenSensor) {
			if(currentData.getDate().isAfter(start) && currentData.getDate().isBefore(end)) {
				result.add(currentData);
			}
		}

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

		//System.out.println("GETTING DATA OF SENSOR " + sensorName + " BETWEEN DATES " + start.getMillis() + " and " + end.getMillis());

		JsonObject sensorData = MongoDBHandler.getInstance().getBddConnector().getSensorDataBetweenDates(sensorName, start.getMillis()/1000, end.getMillis()/1000);
		JsonArray sensorDataValues = sensorData.getAsJsonArray("values");

		List<Data> result = new ArrayList<>();

		for(JsonElement sensorDataValuesElement : sensorDataValues) {
			JsonObject sensorDataValuesObject = sensorDataValuesElement.getAsJsonObject();
			double value = sensorDataValuesObject.get("value").getAsDouble();
			String date = sensorDataValuesObject.get("date").getAsLong() + "";

			Data currentData = new Data(value, Clock.getClock().parseDate(date));
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
