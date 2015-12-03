package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.*;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class WaitForValue extends LevelBuilderGettable implements IActiveDurationnableAndConditionsable {

	private Condition condition;
	private WEEK_PERIOD period = null;
	private DAY_MOMENT moment = null;
	private WaitAfterOn wao = null;

	private Integer atLeast = null;
	private AT_LEAST_TYPE type = null;

	public WaitForValue(Condition condition) {
		this.condition = condition;
		period = WEEK_PERIOD.ALL;
		moment = DAY_MOMENT.ALL;
	}

	@Override
	public ISecondActiveDurationnableAndAndable on(WEEK_PERIOD period, DAY_MOMENT moment) {
		this.period = period;
		this.moment = moment;
		getCondition().setWaitForValue(this);
		return new WaitAfterOn(this);
	}

	@Override
	public ISecondActiveDurationnableAndAndable on(WEEK_PERIOD period) {
		this.period = period;
		getCondition().setWaitForValue(this);
		return new WaitAfterOn(this);
	}

	@Override
	public void end() {
		getLevel().end();
	}

	@Override
	public IConditionable averageOf(String sensor) {
		Condition c = new Condition(this.getCondition().getConditions(), ConditionType.AVERAGE, sensor);
		getLevel().addCondition(c);
		return c;
	}

	@Override
	public IConditionable valueOf(String sensor) {
		Condition c = new Condition(this.getCondition().getConditions(), ConditionType.VALUE_OF, sensor);
		getLevel().addCondition(c);
		return c;
	}

	@Override
	public IImprovable increase(String sensor) {
		Improvement i = new Improvement(condition.getConditions(), sensor, IMPROVEMENT_TYPE.INCREASE);
		return i;
	}

	@Override
	public IImprovable decrease(String sensor) {
		Improvement i = new Improvement(condition.getConditions(), sensor, IMPROVEMENT_TYPE.DECREASE);
		return i;
	}

	void addWaitAfterOn(WaitAfterOn wao) {
		this.wao = wao;
	}

	Condition getCondition() {
		return condition;
	}

	@Override
	public IConditionsable and() {
		return getCondition().getConditions();
	}

	@Override
	public IAtLeastable atLeast(Integer value) {
		setAtLeast(value);
		return new ConditionLeast(this);
	}

	void setAtLeast(Integer atLeast) {
		this.atLeast = atLeast;
	}

	void setType(AT_LEAST_TYPE type) {
		this.type = type;
	}

	Integer getAtLeast() {
		return atLeast;
	}

	AT_LEAST_TYPE getType() {
		return type;
	}

	WEEK_PERIOD getPeriod() {
		return period;
	}

	DAY_MOMENT getMoment() {
		return moment;
	}

	@Override
	protected Level getLevel() {
		return condition.getLevel();
	}

	@Override
	public IRewardableWithIcon atLevel(String levelName) {
		return getLevel().newLevel(levelName);
	}
}
