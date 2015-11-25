package fr.unice.polytech.ecoknowledge.business.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@JsonCreator
	CounterType(@JsonProperty(value = "type", required = true) String counterType) {
		this.counterType = counterType;
	}
}
