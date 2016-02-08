package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sébastien on 02/12/2015.
 */
public enum DayMoment {
	AFTERNOON("afternoon"), MORNING("morning"), DAY("day"), NIGHT("night"), ALL("all");

	private String hours;

	public static DayMoment fromString(String text) {
		if (text != null) {
			for (DayMoment d : DayMoment.values()) {
				if (text.equalsIgnoreCase(d.hours)) {
					return d;
				}
			}
		}
		throw new IllegalArgumentException("Field " + text + " does not exist on daymoment object");
	}

	DayMoment(String hours) {
		this.hours = hours;
	}

	public List<AbstractMap.SimpleEntry<Integer, Integer>> getHours() {

		switch (hours) {
			case ("morning"):
				return Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(8, 11));
			case ("afternoon"):
				return Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(13, 17));
			case ("day"):
				return Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(8, 11), new AbstractMap.SimpleEntry<Integer, Integer>(13, 17));
			case ("night"):
				return Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(21, 6));
			default:
				return Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(0, 23));
		}
	}
}
