package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAtLeastable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class WaitAfterOn extends ChallengeBuilderGettable implements ISecondActiveDurationnableAndAndable {

    private WaitForValue wfv;
    private WEEK_PERIOD period = null;
    private DAY_MOMENT moment = null;
    private Integer atLeast = null;
    private AT_LEAST_TYPE type = null;

    WaitAfterOn(WaitForValue waitForValue) {
        wfv = waitForValue;
    }

    @Override
    public IConditionsable and() {
        return wfv.getCondition().getConditions();
    }

    @Override
    public void sendTo(String IPAddress) {
        getChallengeBuilder().end(IPAddress);
    }

    @Override
    public IAtLeastable atLeast(Integer value) {
        this.atLeast = value;
        return new ConditionLeast(this);
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return wfv.getChallengeBuilder();
    }


    WaitForValue getWfv() {
        return wfv;
    }

    void setType(AT_LEAST_TYPE type) {
        this.type = type;
    }
}
