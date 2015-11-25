package fr.unice.polytech.ecoknowledge.language.api;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ConditionsBuilder {

    private ChallengeBuilder cb;

    public ConditionsBuilder(ChallengeBuilder challengeBuilder) {
        this.cb = challengeBuilder;
    }

    public ConditionBuilder averageOf(String sensor){
        return new ConditionBuilder(this);
    }

    public ConditionBuilder valueOf(String sensor) {
        return new ConditionBuilder(this);
    }

    ChallengeBuilder getCb(){
        return cb;
    }
}

