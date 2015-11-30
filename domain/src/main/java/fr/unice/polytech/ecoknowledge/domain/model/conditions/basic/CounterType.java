package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public enum CounterType {

	TIMES("times"),
	PERCENT_OF_TIME("percent-of-time");

	private String counterType;

	public String getCounterType() {
		return counterType;
	}

	public void setCounterType(String counterType) {
		this.counterType = counterType;
	}


	public static CounterType fromString(String text) {
		if (text != null) {
			for (CounterType b : CounterType.values()) {
				if (text.equalsIgnoreCase(b.counterType)) {
					return b;
				}
			}
		}
		throw new IllegalArgumentException("Field " + text + " does not exist on counter object");
	}

	CounterType(String counterType) {
		this.counterType = counterType;
	}
}
