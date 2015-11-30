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
    private Integer improvementRate = null;

    private OLD_PERIOD improvementPeriod = null;

    public Improvement(Conditions conditions, String sensor) {
        this.conditions = conditions;
        this.sensor = sensor;

    }

    @Override
    public IImprovementPercentable by(int value) {
        improvementRate = value;
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

    void setImprovementPeriod(OLD_PERIOD improvementPeriod) {
        this.improvementPeriod = improvementPeriod;
    }

}
