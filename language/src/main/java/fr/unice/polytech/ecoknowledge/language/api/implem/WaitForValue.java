package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IActiveDurationnableAndConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class WaitForValue extends ChallengeBuilderGettable implements IActiveDurationnableAndConditionsable {

    // TODO not finished yet

    private Condition condition;

    public WaitForValue(Condition condition) {
        this.condition = condition;
    }

    @Override
    public ISecondActiveDurationnableAndAndable on(WEEK_PERIOD period, DAY_MOMENT moment) {
        // HERE USE THE CURRENT CONDITION TOO
        return new WaitAfterOn(this);
    }

    @Override
    public ISecondActiveDurationnableAndAndable on(WEEK_PERIOD period) {
        return new WaitAfterOn(this);
    }

    @Override
    public void build() {
        getChallengeBuilder().build();

    }

    @Override
    public IConditionable averageOf(String sensor) {
        Condition c = new Condition(this.getCondition().getConditions(), ConditionType.AVERAGE, sensor);
        getChallengeBuilder().addCondition(c);
        return c;
    }

    @Override
    public IConditionable valueOf(String sensor) {
        Condition c = new Condition(this.getCondition().getConditions(), ConditionType.VALUE_OF, sensor);
        getChallengeBuilder().addCondition(c);
        return c;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return condition.getChallengeBuilder();
    }

    Condition getCondition(){
        return condition;
    }
}
