package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovable;

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
        Condition c = new Condition(this, ConditionType.AVERAGE, sensor);
        cb.addCondition(c);
        return c;
    }

    @Override
    public IConditionable valueOf(String sensor) {
        Condition c = new Condition(this, ConditionType.VALUE_OF, sensor);
        cb.addCondition(c);
        return c;
    }

    @Override
    public IImprovable increase(String sensor) {
        Improvement i = new Improvement(this, sensor, IMPROVEMENT_TYPE.INCREASE);
        return i;
    }

    @Override
    public IImprovable decrease(String sensor) {
        Improvement i = new Improvement(this, sensor, IMPROVEMENT_TYPE.DECREASE);
        return i;
    }
}
