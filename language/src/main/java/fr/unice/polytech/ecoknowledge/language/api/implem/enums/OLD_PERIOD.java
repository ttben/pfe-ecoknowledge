package fr.unice.polytech.ecoknowledge.language.api.implem.enums;

/**
 * Created by Sébastien on 30/11/2015.
 */
public enum OLD_PERIOD {
	LAST_WEEK("week"), LAST_MONTH("month");

	String when;

	OLD_PERIOD(String when) {
		this.when = when;
	}

	@Override
	public String toString() {
		return when;
	}
}
