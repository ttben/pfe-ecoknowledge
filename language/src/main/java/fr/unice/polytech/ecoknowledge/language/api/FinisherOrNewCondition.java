package fr.unice.polytech.ecoknowledge.language.api;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class FinisherOrNewCondition {

    ActiveDurationBuilder adb;

    public FinisherOrNewCondition(ActiveDurationBuilder activeDurationBuilder) {
        this.adb = activeDurationBuilder;
    }

    public FinisherOrNewCondition andOn(WEEK_PERIOD period, DAY_MOMENT moment){
        return this;
    }

    public CoverageCondition atLeast(Integer value){
        return new CoverageCondition(this);
    }

    public ConditionsBuilder and(){
        return adb.getCb().getCsb();
    }

    public void build() {
        // THIS IS THE END
    }

    ActiveDurationBuilder getAdb(){
        return adb;
    }
}
