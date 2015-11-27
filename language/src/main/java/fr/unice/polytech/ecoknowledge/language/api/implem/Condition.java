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
    private String comparator = null;
    private WaitForValue waitForValue = null;

    public Condition(Conditions conditions, ConditionType type, String sensor) {
        this.conditions = conditions;
        this.type = type;
        this.sensor = sensor;
    }

    @Override
    public IActiveDurationnableAndConditionsable lowerThan(Integer value) {
        this.comparator = "<";
        this.value = value;
        return new WaitForValue(this);
    }

    @Override
    public IActiveDurationnableAndConditionsable greaterThan(Integer value) {
        this.comparator = ">";
        this.value = value;
        return new WaitForValue(this);
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return conditions.getChallengeBuilder();
    }

    Conditions getConditions() {
        return conditions;
    }

    void setWaitForValue(WaitForValue waitForValue) {
        this.waitForValue = waitForValue;
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

    @Override
    public String toString() {
        return "Condition{" +
                "type=" + type +
                ", sensor='" + sensor + '\'' +
                ", value=" + value +
                ", waitForValue=" + waitForValue +
                '}';
    }

    String getComparator() {
        return comparator;
    }
}
