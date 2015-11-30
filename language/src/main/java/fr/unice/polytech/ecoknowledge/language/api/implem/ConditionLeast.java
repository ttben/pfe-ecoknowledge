package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAtLeastable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ConditionLeast extends ChallengeBuilderGettable implements IAtLeastable {

    private WaitForValue wfv;

    public ConditionLeast(WaitForValue waitAfterOn) {
        wfv = waitAfterOn;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return wfv.getChallengeBuilder();
    }

    @Override
    public ISecondActiveDurationnableAndAndable percent() {
        wfv.setType(AT_LEAST_TYPE.PERCENT);
        return wfv;
    }

    @Override
    public ISecondActiveDurationnableAndAndable times() {
        wfv.setType(AT_LEAST_TYPE.TIMES);
        return wfv;
    }

}
