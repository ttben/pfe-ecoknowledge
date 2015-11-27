package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Expression {

	private Comparator comparator;
	private SymbolicName leftOperand;
	private Value rightOperand;

	@JsonCreator
	public Expression(@JsonProperty(value = "leftOperand", required = true) SymbolicName leftOperand,
					  @JsonProperty(value = "comparator", required = true) Comparator comparator,
					  @JsonProperty(value = "rightOperand", required = true) Value rightOperand) {
		this.comparator = comparator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}


	public SymbolicName getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(SymbolicName leftOperand) {
		this.leftOperand = leftOperand;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public Value getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(Value rightOperand) {
		this.rightOperand = rightOperand;
	}

	public boolean compareWith(Double value) {
		return (getLeftOperand().isRequired()) ?
				this.comparator.compare(value, getRightOperand().getSymbolicName())
				: this.comparator.compare(getRightOperand().getSymbolicName(), value);
	}

	public String getDescription() {
		return "" + leftOperand.getSymbolicName() + " " + comparator.toString() + " " + rightOperand.getSymbolicName();
	}
}