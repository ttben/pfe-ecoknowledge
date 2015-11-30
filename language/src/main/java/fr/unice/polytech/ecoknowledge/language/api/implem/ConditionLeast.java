package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAndable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAtLeastable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ConditionLeast extends ChallengeBuilderGettable implements IAtLeastable {

    private WaitAfterOn wao;

    public ConditionLeast(WaitAfterOn waitAfterOn) {
        wao = waitAfterOn;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return wao.getChallengeBuilder();
    }

    @Override
    public ISecondActiveDurationnableAndAndable percent() {
        wao.setType(AT_LEAST_TYPE.PERCENT);
        return wao;
    }

    @Override
    public ISecondActiveDurationnableAndAndable times() {
        wao.setType(AT_LEAST_TYPE.TIMES);
        return wao;
    }

}
