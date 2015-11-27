package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDuringable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardable;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class During extends ChallengeBuilderGettable implements IDuringable {

    ChallengeBuilder cb;

    During(ChallengeBuilder cb){
        this.cb = cb;
    }

    @Override
    public IRewardable during(Integer value, DURATION_TYPE type) {
        getChallengeBuilder().setTime(value);
        getChallengeBuilder().setType(type);
        return new Rewards(this);
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return cb;
    }
}
