package fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions;

import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract  class ConditionViewFactory {

	public final static ConditionView getView(Condition condition) {

		if(condition instanceof StandardCondition) {
			return new StandardConditionView((StandardCondition)condition);
		}

		throw new NotImplementedException();
	}
}
