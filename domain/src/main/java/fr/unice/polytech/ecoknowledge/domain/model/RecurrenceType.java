package fr.unice.polytech.ecoknowledge.domain.model;

/**
 * Created by Benjamin on 30/11/2015.
 */
public enum RecurrenceType {

	DAY("day"),
	WEEK("week"),
	MONTH("month");

	private String recurrenceType;

	public String getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public static RecurrenceType fromString(String text) {
		if (text != null) {
			for (RecurrenceType b : RecurrenceType.values()) {
				if (text.equalsIgnoreCase(b.recurrenceType)) {
					return b;
				}
			}
		}
		throw new IllegalArgumentException("Field " + text + " does not exist on counter object");
	}

	RecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}
}
