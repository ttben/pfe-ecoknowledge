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

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public TimeFilter getTargetDays() {
		return targetDays;
	}

	public void setTargetDays(TimeFilter targetDays) {
		this.targetDays = targetDays;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BasicCondition)) {
			return false;
		}

		BasicCondition basicCondition = (BasicCondition) obj;

		// FIXME: 30/11/2015 targetDays can not be null (not yet implemented)
		if(targetDays != null) {
			return expression.equals(basicCondition.expression)
					&& targetDays.equals(basicCondition.targetDays);
		} else {
			return expression.equals(basicCondition.expression);
		}
	}

}
