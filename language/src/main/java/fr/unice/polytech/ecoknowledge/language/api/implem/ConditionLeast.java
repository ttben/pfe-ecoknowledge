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
        if(wfv.getAtLeast() > 100){
            throw new IllegalArgumentException("Can't have more than 100% time condition");
        } else if(wfv.getAtLeast() < 1){
            throw new IllegalArgumentException("Can't have less than 1% time condition");
        }
        wfv.setType(AT_LEAST_TYPE.PERCENT);
        return wfv;
    }

    @Override
    public ISecondActiveDurationnableAndAndable times() {
        if(wfv.getAtLeast() < 1){
            throw new IllegalArgumentException("Can't have less than 1 time condition");
        }
        wfv.setType(AT_LEAST_TYPE.TIMES);
        return wfv;
    }

}
