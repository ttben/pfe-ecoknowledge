package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.TimeFilter;

public abstract class BasicCondition implements Condition {

	protected Expression expression;

	protected TimeFilter targetTime;

	public BasicCondition(Expression expression, TimeFilter targetDays) {

		this.expression = expression;
		this.targetTime = targetDays;
	}

	public Expression getExpression() {
		return expression;
	}

	public TimeFilter getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(TimeFilter targetTime) {
		this.targetTime = targetTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BasicCondition)) {
			return false;
		}

		BasicCondition basicCondition = (BasicCondition) obj;

			return expression.equals(basicCondition.expression)
					&& targetTime.equals(basicCondition.targetTime);

	}
}