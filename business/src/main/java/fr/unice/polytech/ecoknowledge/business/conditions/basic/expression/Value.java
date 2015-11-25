package fr.unice.polytech.ecoknowledge.business.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Value implements Operand {

	private double value;

	@JsonCreator
	public Value(@JsonProperty(value = "value", required = true) Double value) {
		this.value = value;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public Double getValue() {
		return this.value;
	}
}