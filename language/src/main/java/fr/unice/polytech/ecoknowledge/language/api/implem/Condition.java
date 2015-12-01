package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IActiveDurationnable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IActiveDurationnableAndConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Condition extends LevelBuilderGettable implements IConditionable {

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
    public IActiveDurationnable equalsTo(Integer value) {
        this.comparator = "=";
        this.value = value;
        return new WaitForValue(this);
    }

    @Override
    public IActiveDurationnable differentFrom(Integer value) {
        this.comparator = "!=";
        this.value = value;
        return new WaitForValue(this);
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

    String getComparator() {
        return comparator;
    }

    WaitForValue getWfv() {
        return waitForValue;
    }

    @Override
    protected Level getLevel() {
        return conditions.getLevel();
    }
}
