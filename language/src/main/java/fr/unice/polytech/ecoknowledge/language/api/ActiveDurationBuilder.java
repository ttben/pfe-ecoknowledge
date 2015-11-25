package fr.unice.polytech.ecoknowledge.language.api;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ActiveDurationBuilder {

    private ConditionBuilder cb;

    public ActiveDurationBuilder(ConditionBuilder conditionBuilder) {
        this.cb = conditionBuilder;
    }

    public FinisherOrNewCondition on(WEEK_PERIOD period, DAY_MOMENT moment){
        return new FinisherOrNewCondition(this);
    }


    ConditionBuilder getCb() {
        return cb;
    }

}
