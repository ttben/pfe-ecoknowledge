package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IActiveDurationnableAndConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Condition extends ChallengeBuilderGettable implements IConditionable {

    private Conditions conditions;

    public Condition(Conditions conditions) {
        this.conditions = conditions;
    }

    @Override
    public IActiveDurationnableAndConditionsable lowerThan(Integer value) {
        return new WaitForValue(this);
    }

    @Override
    public IActiveDurationnableAndConditionsable greaterThan(Integer value) {
        return new WaitForValue(this);
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return conditions.getChallengeBuilder();
    }

    Conditions getConditions() {
        return conditions;
    }
}
