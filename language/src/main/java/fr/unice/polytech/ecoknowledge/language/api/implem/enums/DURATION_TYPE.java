package fr.unice.polytech.ecoknowledge.language.api.implem.enums;

/**
 * Created by Sébastien on 25/11/2015.
 */
public enum DURATION_TYPE {
	DAY("day"), WEEK("week"), MONTH("month"), NONE("none");

	String name;

	DURATION_TYPE(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
