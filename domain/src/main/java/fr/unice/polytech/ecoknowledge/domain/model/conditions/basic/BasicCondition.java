package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.TimeFilter;

public abstract class BasicCondition implements Condition {

	protected Expression expression;
	protected TimeFilter targetDays;

	public BasicCondition(Expression expression, TimeFilter targetDays) {

		this.expression = expression;
		this.targetDays = targetDays;
	}

	public Expression getExpression() {
		return expression;
	}

	public TimeFilter getTargetDays() {
		return targetDays;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BasicCondition)) {
			return false;
		}

		BasicCondition basicCondition = (BasicCondition) obj;

			return expression.equals(basicCondition.expression)
					&& targetDays.equals(basicCondition.targetDays);

	}
}