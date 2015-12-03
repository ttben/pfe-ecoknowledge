package fr.unice.polytech.ecoknowledge.domain.calculator;

import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class Cache {

	private Map<String, List<Data>> data = new HashMap<>();

	public List<Data> getDataOf(String sensorName) {
		return this.data.get(sensorName);
	}

	public List<Data> getDataOfSensorBetweenDate(String sensorName,
												 DateTime start, DateTime end) {
		return this.getDataOf(sensorName); // FIXME: 26/11/2015
	}

	public List<Data> getDataOfSensorBetweenDate(String sensorName, DateTime start, DateTime end,
												 AbstractMap.SimpleEntry<Integer, Integer> weekMoment,
												 List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment){
		System.out.println("\n\n+\tAccessing cache ... Data saved : \n"+ this.data.toString());


		System.out.printf("\n\n+\tWant to access data of : " + sensorName);
		List<Data> result = this.getDataOf(sensorName);

		if(result == null) {
			result = new ArrayList<>();
		}

		System.out.println("\n\n+\tReturning : \n"+ result.toString());

		return result;
	}

	public Map<String, List<Data>> getData() {
		return data;
	}

	public void setData(Map<String, List<Data>> data) {
		this.data = data;
	}

	public static Cache getFakeCache() {
		Cache cache = new Cache();

		Map<String, List<Data>> fakedData = new HashMap<>();

		List<Data> aListOfData = new ArrayList<>();
		aListOfData.add(new Data(20.0,new DateTime().minusDays(1)));
		aListOfData.add(new Data(22.0,new DateTime().minusDays(1)));
		fakedData.put("TEMP_443V", aListOfData);

		List<Data> anotherListOfData = new ArrayList<>();
		anotherListOfData.add(new Data(20.0,new DateTime().minusDays(1)));
		anotherListOfData.add(new Data(22.0,new DateTime().minusDays(1)));
		fakedData.put("TEMP_555", anotherListOfData);

		cache.setData(fakedData);

		return cache;
	}

}
