package fr.unice.polytech.ecoknowledge.business.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Counter {
	private Double threshold;
	private CounterType counterType;

	@JsonCreator
	public Counter(@JsonProperty(value = "threshold", required = true) Double threshold, @JsonProperty(value = "counterType", required = true) CounterType counterType) {
		this.threshold = threshold;
		this.counterType = counterType;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public CounterType getCounterType() {
		return counterType;
	}

	public void setCounterType(CounterType counterType) {
		this.counterType = counterType;
	}
}
