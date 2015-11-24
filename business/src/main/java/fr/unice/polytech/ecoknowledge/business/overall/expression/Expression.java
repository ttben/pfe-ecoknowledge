package fr.unice.polytech.ecoknowledge.business.overall.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Expression {

	private Comparator comparator;
	private Operand leftOperand;
	private Operand rightOperand;

	@JsonCreator
	public Expression(@JsonProperty("leftOperand")Operand leftOperand, @JsonProperty("comparator")Comparator comparator, @JsonProperty("rightOperand")Operand rightOperand) {
		this.comparator = comparator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

}