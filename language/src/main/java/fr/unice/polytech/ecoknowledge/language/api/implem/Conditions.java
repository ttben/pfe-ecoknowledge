package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Conditions extends LevelBuilderGettable implements IConditionsable {

	private Level l;

	Conditions(Level level) {
		this.l = level;
	}

	protected Level getLevel() {
		return l;
	}

	@Override
	public IConditionable averageOf(String sensor) {
		Condition c = new Condition(this, ConditionType.AVERAGE, sensor);
		l.addCondition(c);
		return c;
	}

	@Override
	public IConditionable valueOf(String sensor) {
		Condition c = new Condition(this, ConditionType.VALUE_OF, sensor);
		l.addCondition(c);
		return c;
	}

	@Override
	public IImprovable increase(String sensor) {
		Improvement i = new Improvement(this, sensor, IMPROVEMENT_TYPE.INCREASE);
		return i;
	}

	@Override
	public IImprovable decrease(String sensor) {
		Improvement i = new Improvement(this, sensor, IMPROVEMENT_TYPE.DECREASE);
		return i;
	}
}
