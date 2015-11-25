package fr.unice.polytech.ecoknowledge.language.api.implem;

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
        return wao;
    }


}
