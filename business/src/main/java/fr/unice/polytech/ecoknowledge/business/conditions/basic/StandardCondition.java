package fr.unice.polytech.ecoknowledge.business.conditions.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.business.conditions.Day;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.expression.Expression;

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
}