package fr.unice.polytech.ecoknowledge.domain.model.conditions.improve;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum VariationType {
	INCREASE("increase"),
	DECREASE("decrease"),
	STABILITY("stability");

	private String variationType;

	@JsonCreator
	VariationType(@JsonProperty(value = "variationType", required = true) String variationType) {
		this.variationType = variationType;
	}

	public String getVariationType() {
		return variationType;
	}

	public void setVariationType(String variationType) {
		this.variationType = variationType;
	}
}
