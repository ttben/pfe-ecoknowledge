package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.ecoknowledge.domain.model.serializer.CounterSerializer;

@JsonSerialize(using = CounterSerializer.class)
@JsonDeserialize(using = CounterDeserializer.class)
public class Counter {
	private Integer threshold;
	private CounterType type;

	@JsonCreator
	public Counter(@JsonProperty(value = "threshold", required = true) Integer threshold,
				   @JsonProperty(value = "type", required = true) CounterType type) {
		this.threshold = threshold;
		this.type = type;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public CounterType getType() {
		return type;
	}

	public void setType(CounterType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return (type.equals(CounterType.PERCENT_OF_TIME))
				? " " + threshold + "% du temps"
				: " " + threshold + " fois";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Counter)) {
			return false;
		}

		Counter counter = (Counter) obj;

		return threshold.equals(counter.threshold)
				&& type.equals(counter.type);
	}
}
