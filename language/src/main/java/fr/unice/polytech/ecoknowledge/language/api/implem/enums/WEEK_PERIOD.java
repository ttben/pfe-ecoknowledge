package fr.unice.polytech.ecoknowledge.language.api.implem.enums;

/**
 * Created by Sébastien on 25/11/2015.
 */
public enum WEEK_PERIOD {
	WEEK_DAYS("weekDays"), WEEK_END("weekEnd"), ALL("all");

	String name;

	WEEK_PERIOD(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
