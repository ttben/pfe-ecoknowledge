package fr.unice.polytech.ecoknowledge.domain.model.conditions.improve;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.calculator.ConditionResult;

import java.util.List;

public class ImproveCondition implements Condition{

	private TimeBox comparedPeriod;
	private Double threshold;

	@JsonCreator
	public ImproveCondition(@JsonProperty(value = "referencePeriod", required = true) TimeBox comparedPeriod,
							@JsonProperty(value = "threshold", required = true) Double threshold) {
		this.comparedPeriod = comparedPeriod;
		this.threshold = threshold;
	}

	public TimeBox getComparedPeriod() {
		return comparedPeriod;
	}

	public void setComparedPeriod(TimeBox comparedPeriod) {
		this.comparedPeriod = comparedPeriod;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	@Override
	public void accept(Calculator t, Goal goal, List<ConditionResult> conditionResults) {
		t.evaluateCondition(this, goal, conditionResults);
	}
}