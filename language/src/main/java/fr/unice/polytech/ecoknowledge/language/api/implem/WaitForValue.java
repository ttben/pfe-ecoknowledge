package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IActiveDurationnableAndConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ISecondActiveDurationnableAndAndable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class WaitForValue extends ChallengeBuilderGettable implements IActiveDurationnableAndConditionsable {

    private Condition condition;
    private WEEK_PERIOD period = null;
    private DAY_MOMENT moment = null;
    private WaitAfterOn wao = null;

    public WaitForValue(Condition condition) {
        this.condition = condition;
    }

    @Override
    public ISecondActiveDurationnableAndAndable on(WEEK_PERIOD period, DAY_MOMENT moment) {
        this.period = period;
        this.moment = moment;
        getCondition().addWaitForValue(this);
        return new WaitAfterOn(this);
    }

    @Override
    public ISecondActiveDurationnableAndAndable on(WEEK_PERIOD period) {
        this.period = period;
        getCondition().addWaitForValue(this);
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

    void addWaitAfterOn(WaitAfterOn wao){
        this.wao = wao;
    }

    Condition getCondition(){
        return condition;
    }

    @Override
    public String toString() {
        return "WaitForValue{" +
                "wao=" + wao +
                ", moment=" + moment +
                ", period=" + period +
                '}';
    }

    @Override
    public IConditionsable and() {
        return getCondition().getConditions();
    }
}
