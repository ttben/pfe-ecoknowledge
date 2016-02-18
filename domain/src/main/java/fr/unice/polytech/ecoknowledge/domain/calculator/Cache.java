package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class Cache {

	private static Cache fakeCacheInstance;
	private Map<String, List<Data>> data = new HashMap<>();

	public static Cache getFakeCache() {
		if (fakeCacheInstance == null) {
			fakeCacheInstance = new Cache();

			Map<String, List<Data>> fakedData = new HashMap<>();
			fakedData.put("TEMP_443V", new ArrayList<>());
			fakedData.put("TEMP_555", new ArrayList<>());

/*
			List<Data> aListOfData = new ArrayList<>();
			aListOfData.add(new Data(20.0, new DateTime().minusDays(1)));
			aListOfData.add(new Data(22.0, new DateTime().minusDays(1)));
			fakedData.put("TEMP_443V", aListOfData);

			List<Data> anotherListOfData = new ArrayList<>();
			anotherListOfData.add(new Data(20.0, new DateTime().minusDays(1)));
			anotherListOfData.add(new Data(22.0, new DateTime().minusDays(1)));
			fakedData.put("TEMP_555", anotherListOfData);
*/
			fakeCacheInstance.setData(fakedData);
		}

		return fakeCacheInstance;
	}

	public List<Data> getDataOf(String sensorName) {
		ArrayList<Data> datas = new ArrayList<>();

		if (data.get(sensorName) != null)
			datas.addAll(data.get(sensorName));
		return datas;
	}

	public List<Data> getDataOfSensorBetweenDate(String sensorName,
												 DateTime start, DateTime end) {

		MongoDBHandler.getInstance().readAllSensorData();

		ArrayList<Data> data = new ArrayList<>();
		for (Data d : getDataOf(sensorName)) {
			if (d.getDate().isBefore(end) && d.getDate().isAfter(start))
				data.add(d);
		}
		return data;

	}

	public List<Data> getDataOfSensorBetweenDate(String sensorName, DateTime start, DateTime end,
												 AbstractMap.SimpleEntry<Integer, Integer> weekMoment,
												 List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment) {
		List<Data> data = getDataOfSensorBetweenDate(sensorName, start, end);
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
