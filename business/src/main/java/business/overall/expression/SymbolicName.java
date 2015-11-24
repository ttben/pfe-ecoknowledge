package business.overall.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymbolicName implements Operand {

	private String name;

	@JsonCreator
	public SymbolicName(@JsonProperty("symbolicName") String name) {
		this.name = name;
	}

	@Override
	public boolean isRequired() {
		return true;
	}
}