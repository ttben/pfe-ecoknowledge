package fr.unice.polytech.ecoknowledge.business.overall.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = Value.class, name = "Value"),
		@JsonSubTypes.Type(value = SymbolicName.class, name = "SymbolicName") }
)
public interface Operand {

	boolean isRequired();

}