package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Comparator {

	private String comparator;

	@JsonCreator
	public Comparator(String comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean equals(Object o){
		if(! (o instanceof Comparator)) {
			return false;
		}

		Comparator otherComparator = (Comparator)o;
		return otherComparator.comparator.equals(this.comparator);
	}

	public boolean compare(Double value, Double value1) {
		switch (comparator) {
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
}