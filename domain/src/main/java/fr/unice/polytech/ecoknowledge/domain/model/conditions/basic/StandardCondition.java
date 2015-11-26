package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Day;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Operand;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.calculator.ConditionResult;

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
							 @JsonProperty(value = "counter", required = false) Counter counter) {      // FIXME: 25/11/2015 required must be true

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
		if(this.expression.getLeftOperand().isRequired()) {
			return this.expression.getLeftOperand();
		}

		if(this.expression.getRightOperand().isRequired()) {
			return this.expression.getRightOperand();
		}

		return null;
	}

	public boolean compareWith(Double value) {
		return this.expression.compareWith(value);
	}

	@Override
	public void accept(Calculator t, Goal goal, List<ConditionResult> conditionResults) {
		t.evaluateCondition(this, goal, conditionResults);
	}

	public String getDescription() {
		return expression.getDescription() + " " + counter.toString();
	}
}