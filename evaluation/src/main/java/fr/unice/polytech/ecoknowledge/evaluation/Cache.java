package fr.unice.polytech.ecoknowledge.evaluation;

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
												 Date start, Date end) {
		return this.getDataOf(sensorName); // FIXME: 26/11/2015
	}

	public Map<String, List<Data>> getData() {
		return data;
	}

	public void setData(Map<String, List<Data>> data) {
		this.data = data;
	}
}
