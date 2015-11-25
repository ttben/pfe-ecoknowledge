package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Conditions extends ChallengeBuilderGettable implements IConditionsable {

    private ChallengeBuilder cb;

    Conditions(ChallengeBuilder cb){
        this.cb = cb;
    }

    ChallengeBuilder getChallengeBuilder(){
        return cb;
    }

    @Override
    public IConditionable averageOf(String sensor) {
        Condition c = new Condition(this);
        cb.addCondition(c);
        return c;
    }

    @Override
    public IConditionable valueOf(String sensor) {
        Condition c = new Condition(this);
        return c;
    }
}
