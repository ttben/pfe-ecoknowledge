package fr.unice.polytech.ecoknowledge.business.conditions.basic;

import fr.unice.polytech.ecoknowledge.business.conditions.Condition;
import fr.unice.polytech.ecoknowledge.business.conditions.Day;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicCondition implements Condition {

	private Expression expression;
	private List<Day> targetDays = new ArrayList<>();

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
