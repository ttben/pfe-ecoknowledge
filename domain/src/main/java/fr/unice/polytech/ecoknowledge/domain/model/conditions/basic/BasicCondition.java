package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Day;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicCondition implements Condition {

	protected Expression expression;
	protected List<Day> targetDays = new ArrayList<>();

	public BasicCondition(Expression expression, List<Day> targetDays) {

		this.expression = expression;
		this.targetDays = targetDays;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public List<Day> getTargetDays() {
		return targetDays;
	}

	public void setTargetDays(List<Day> targetDays) {
		this.targetDays = targetDays;
	}
}