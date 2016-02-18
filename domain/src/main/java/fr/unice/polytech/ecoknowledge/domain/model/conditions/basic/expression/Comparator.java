package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comparator {

	private String type;

	@JsonCreator
	public Comparator(@JsonProperty(value = "type", required = true) String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean compare(Double value, Double value1) {
		switch (type) {
			case ">":
				return value.doubleValue() > value1.doubleValue();
			case "<":
				return value.doubleValue() < value1.doubleValue();
			case "=":
				return value.equals(value1);
			case "!=":
				return !value.equals(value1);
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Comparator)) {
			return false;
		}

		Comparator otherComparator = (Comparator) o;
		return otherComparator.type.equals(this.type);
	}

	@Override
	public String toString() {
		return this.type;
	}
}