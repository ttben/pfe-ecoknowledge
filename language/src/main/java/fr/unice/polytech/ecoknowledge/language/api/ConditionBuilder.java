package fr.unice.polytech.ecoknowledge.language.api;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ConditionBuilder {

    private ConditionsBuilder csb;

    public ConditionBuilder(ConditionsBuilder conditionsBuilder) {
        csb = conditionsBuilder;
    }

    public ActiveDurationBuilder lowerThan(Integer value){
        return new ActiveDurationBuilder(this);
    }

    public ActiveDurationBuilder greaterThan(Integer value){
        return new ActiveDurationBuilder(this);
    }

    ConditionsBuilder getCsb() {
        return csb;
    }
}
