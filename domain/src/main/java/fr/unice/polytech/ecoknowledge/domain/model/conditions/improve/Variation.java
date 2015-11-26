package fr.unice.polytech.ecoknowledge.domain.model.conditions.improve;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Variation {
	private VariationType variationType;
	private Double threshold;

	@JsonCreator
	public Variation(@JsonProperty(value = "variation-type", required = true) VariationType variationType,
					 @JsonProperty(value = "value", required = true)Double threshold) {
		this.variationType = variationType;
		this.threshold = threshold;
	}

	public VariationType getVariationType() {
		return variationType;
	}

	public void setVariationType(VariationType variationType) {
		this.variationType = variationType;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
}
