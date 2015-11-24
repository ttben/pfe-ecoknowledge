package fr.unice.polytech.ecoknowledge.business.overall.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Value implements Operand {

	private double value;

	@JsonCreator
	public Value(@JsonProperty("value")Double value) {
		this.value = value;
	}

	@Override
	public boolean isRequired() {
		return false;
	}
}