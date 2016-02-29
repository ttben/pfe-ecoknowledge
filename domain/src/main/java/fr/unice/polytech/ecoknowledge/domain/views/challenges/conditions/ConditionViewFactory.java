package fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions;

import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

public abstract class ConditionViewFactory {

	public final static ConditionView getView(Condition condition) {

		if (condition instanceof StandardCondition) {
			return new StandardConditionView((StandardCondition) condition);
		} else if (condition instanceof ImproveCondition) {
			return new ImproveConditionView((ImproveCondition) condition);
		}

		throw new IllegalArgumentException();
	}
}
