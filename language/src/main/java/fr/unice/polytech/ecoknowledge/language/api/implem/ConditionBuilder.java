package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsableAfterReward;

import java.util.ArrayList;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class ConditionBuilder extends ChallengeBuilderGettable implements IConditionsableAfterReward {

    Rewards r;


    public ConditionBuilder(Rewards rewards) {
        r = rewards;
    }

    @Override
    public IConditionsable withConditions() {
        Conditions c = new Conditions(getChallengeBuilder());
        return c;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return r.getChallengeBuilder();
    }
}
