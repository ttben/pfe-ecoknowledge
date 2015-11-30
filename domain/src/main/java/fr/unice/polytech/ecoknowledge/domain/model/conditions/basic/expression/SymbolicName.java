package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymbolicName implements Operand {

	private String name;

	@JsonCreator
	public SymbolicName(@JsonProperty(value = "symbolicName", required = true) String name) {
		this.name = name;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public String getSymbolicName() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SymbolicName)) {
			return false;
		}

		SymbolicName symbolicName = (SymbolicName) obj;

		return name.equals(symbolicName.name);
	}
}