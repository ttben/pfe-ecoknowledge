package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymbolicName {

	private String symbolicName;

	@JsonCreator
	public SymbolicName(@JsonProperty(value = "symbolicName", required = true) String name) {
		this.symbolicName = name;
	}

	public String getSymbolicName() {
		return this.symbolicName;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SymbolicName)) {
			return false;
		}

		SymbolicName symbolicName = (SymbolicName) obj;

		return this.symbolicName.equals(symbolicName.symbolicName);
	}
}