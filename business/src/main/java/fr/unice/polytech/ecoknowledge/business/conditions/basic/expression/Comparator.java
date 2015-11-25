package fr.unice.polytech.ecoknowledge.business.conditions.basic.expression;

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
}