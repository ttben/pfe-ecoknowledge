package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAtLeastable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class WaitAfterOn extends ChallengeBuilderGettable implements ISecondActiveDurationnableAndAndable {

    // TODO not finished yet

    private WaitForValue wfv;

    WaitAfterOn(WaitForValue waitForValue) {
        wfv = waitForValue;
    }

    @Override
    public IConditionsable and() {
        return wfv.getCondition().getConditions();
    }

    @Override
    public void build() {
        getChallengeBuilder().build();
    }

    @Override
    public ISecondActiveDurationnableAndAndable andOn(WEEK_PERIOD period, DAY_MOMENT moment) {
        // TODO Add duration in a collection FIRST


        return this;
    }

    @Override
    public IAtLeastable atLeast(Integer value) {
        return new ConditionLeast(this);
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return wfv.getChallengeBuilder();
    }
}
