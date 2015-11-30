package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Day;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Operand;

import java.util.List;

/**
 * a < b, 		week days		80% of time
 * expression		targetDays		thresholdRate
 */
public class StandardCondition extends BasicCondition {

	private Counter counter;

	@JsonCreator
	public StandardCondition(@JsonProperty(value = "expression", required = true) Expression expression,
							 @JsonProperty(value = "targetDays", required = false) List<Day> targetDays,    // FIXME: 25/11/2015 required must be true
							 @JsonProperty(value = "counter", required = true) Counter counter) {

		super(expression, targetDays);
		this.counter = counter;
	}

	public Counter getCounter() {
		return counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	public Operand getRequiredOperand() {
		if (this.expression.getLeftOperand().isRequired()) {
			return this.expression.getLeftOperand();
		}

		if (this.expression.getRightOperand().isRequired()) {
			return this.expression.getRightOperand();
		}

		return null;
	}

	public boolean compareWith(Double value) {
		return this.expression.compareWith(value);
	}

	public String getDescription() {
		String result = expression.getDescription();
		result = result.concat(counter.toString());
		return result;
	}

	@Override
	public void accept(GoalVisitor goalVisitor) {
		goalVisitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StandardCondition)) {
			return false;
		}

		StandardCondition standardCondition = (StandardCondition) obj;

		return super.equals(standardCondition)
				&& counter.equals(standardCondition.counter);
	}
}