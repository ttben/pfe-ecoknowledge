package fr.unice.polytech.ecoknowledge.language.overall.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Expression {

	private Comparator comparator;
	private Operand leftOperand;
	private Operand rightOperand;

	@JsonCreator
	public Expression(@JsonProperty(value = "leftOperand", required = true) Operand leftOperand,
					  @JsonProperty(value = "comparator", required = true) Comparator comparator,
					  @JsonProperty(value = "rightOperand", required = true) Operand rightOperand) {
		this.comparator = comparator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}


	public Operand getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(Operand leftOperand) {
		this.leftOperand = leftOperand;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public Operand getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(Operand rightOperand) {
		this.rightOperand = rightOperand;
	}
}