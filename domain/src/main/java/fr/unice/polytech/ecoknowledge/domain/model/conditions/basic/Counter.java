package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CounterDeserializer.class)
public class Counter {
	private Integer threshold;
	private CounterType counterType;

	@JsonCreator
	public Counter(@JsonProperty(value = "threshold", required = true) Integer threshold,
				   @JsonProperty(value = "type", required = true) CounterType counterType) {
		this.threshold = threshold;
		this.counterType = counterType;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public CounterType getCounterType() {
		return counterType;
	}

	public void setCounterType(CounterType counterType) {
		this.counterType = counterType;
	}

	@Override
	public String toString() {
		return (counterType.equals(CounterType.PERCENT_OF_TIME))
				? " " + threshold + "% du temps"
				: " " + threshold + " fois";
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Counter)) {
			return false;
		}

		Counter counter = (Counter) obj;

		return threshold.equals(counter.threshold)
				&& counterType.equals(counter.counterType);
	}
}
