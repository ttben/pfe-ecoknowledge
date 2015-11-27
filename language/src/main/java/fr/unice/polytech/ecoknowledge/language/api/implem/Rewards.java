package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsableAfterReward;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardable;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class Rewards extends ChallengeBuilderGettable implements IRewardable {

    During d;

    public Rewards(During during) {
        d = during;
    }


    @Override
    public IConditionsableAfterReward rewards(Integer points) {
        getChallengeBuilder().setPoints(points);
        return new ConditionBuilder(this);
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return d.getChallengeBuilder();
    }
}
