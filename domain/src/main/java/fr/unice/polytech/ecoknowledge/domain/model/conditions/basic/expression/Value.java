package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {

	private double value;

	@JsonCreator
	public Value(@JsonProperty(value = "value", required = true) Double value) {
		this.value = value;
	}

	public Double getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Value)) {
			return false;
		}

		Value value = (Value) obj;

		return this.value == value.value;
	}
}