package fr.unice.polytech.ecoknowledge.language.overall.expression;

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
	public String getValue() {
		return this.name;
	}
}