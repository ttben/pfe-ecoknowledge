package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAtLeastable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardableWithIcon;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class WaitAfterOn extends LevelBuilderGettable implements ISecondActiveDurationnableAndAndable {

	private WaitForValue wfv;
	private WEEK_PERIOD period = null;
	private DAY_MOMENT moment = null;

	WaitAfterOn(WaitForValue waitForValue) {
		wfv = waitForValue;
	}

	@Override
	public IConditionsable and() {
		return wfv.getCondition().getConditions();
	}

	@Override
	public void end() {
		getLevel().end();
	}

	@Override
	public IAtLeastable atLeast(Integer value) {
		wfv.setAtLeast(value);
		return new ConditionLeast(wfv);
	}

	WaitForValue getWfv() {
		return wfv;
	}

	void setType(AT_LEAST_TYPE type) {
		wfv.setType(type);
	}

	@Override
	public IRewardableWithIcon atLevel(String levelName) {
		return getLevel().newLevel(levelName);
	}

	@Override
	protected Level getLevel() {
		return wfv.getLevel();
	}
}
