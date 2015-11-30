package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAndable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class AndOfImprovement extends ChallengeBuilderGettable implements IAndable {

    private Conditions conditions;

    public AndOfImprovement(Conditions conditions) {
        this.conditions = conditions;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return conditions.getChallengeBuilder();
    }

    @Override
    public IConditionsable and() {
        return conditions;
    }

    @Override
    public void end() {
        getChallengeBuilder().end();
    }
}
