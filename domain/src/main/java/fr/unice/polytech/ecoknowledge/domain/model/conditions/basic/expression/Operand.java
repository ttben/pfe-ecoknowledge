package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = Value.class, name = "value"),
		@JsonSubTypes.Type(value = SymbolicName.class, name = "symbolicName")}
)
public interface Operand {

	boolean isRequired();

	Object getSymbolicName();
}