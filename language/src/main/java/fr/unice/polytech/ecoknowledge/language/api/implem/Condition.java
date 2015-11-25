package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IActiveDurationnableAndConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Condition extends ChallengeBuilderGettable implements IConditionable {

    private Conditions conditions;
    private ConditionType type = null;
    private String sensor = null;
    private Integer value = null;

    public Condition(Conditions conditions, ConditionType type, String sensor) {
        this.conditions = conditions;
        this.type = type;
        this.sensor = sensor;
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

    @Override
    public String toString() {
        return "Condition{" +
                "type=" + type +
                ", sensor='" + sensor + '\'' +
                ", value=" + value +
                '}';
    }

    ConditionType getType() {
        return type;
    }

    String getSensor() {
        return sensor;
    }

    Integer getValue() {
        return value;
    }
}
