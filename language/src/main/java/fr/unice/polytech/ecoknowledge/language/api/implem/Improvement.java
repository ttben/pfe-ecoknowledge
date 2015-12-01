package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovementPercentable;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class Improvement extends ChallengeBuilderGettable implements IImprovable {

    private Conditions conditions;
    private String sensor;

    private Integer improvementValue = null;

    private OLD_PERIOD improvementPeriod = null;

    public Improvement(Conditions conditions, String sensor) {
        this.conditions = conditions;
        this.sensor = sensor;
        getChallengeBuilder().addImprovement(this);
    }

    @Override
    public IImprovementPercentable by(int value) {
        if(value > 100){
        throw new IllegalArgumentException("Can't have more than 100% time condition");
    } else if(value < 1){
        throw new IllegalArgumentException("Can't have less than 1% time condition");
    }
        improvementValue = value;
        Percentable p = new Percentable(this);
        return p;
    }


    Conditions getConditions() {
        return conditions;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return conditions.getChallengeBuilder();
    }


    Integer getImprovementValue() {
        return improvementValue;
    }

    String getSensor() {
        return sensor;
    }

    OLD_PERIOD getImprovementPeriod() {
        return improvementPeriod;
    }

    void setImprovementPeriod(OLD_PERIOD improvementPeriod) {
        this.improvementPeriod = improvementPeriod;
    }

}
