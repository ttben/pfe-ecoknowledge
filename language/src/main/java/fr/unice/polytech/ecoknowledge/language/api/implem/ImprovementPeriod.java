package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IAndable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovementFinished;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class ImprovementPeriod extends ChallengeBuilderGettable implements IImprovementFinished {

    private Percentable p;

    public ImprovementPeriod(Percentable percentable) {
        this.p = percentable;
    }

    @Override
    ChallengeBuilder getChallengeBuilder() {
        return p.getChallengeBuilder();
    }

    @Override
    public IAndable comparedTo(OLD_PERIOD when) {
        p.getImprovement().setImprovementPeriod(when);
        return new AndOfImprovement(p.getImprovement().getConditions());
    }
}
