package fr.unice.polytech.ecoknowledge.language.api;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class CoverageCondition {

    private FinisherOrNewCondition fonc;

    public CoverageCondition(FinisherOrNewCondition finisherOrNewCondition) {
        this.fonc = finisherOrNewCondition;
    }

    public FinisherOrNewCondition percent(){
        return fonc;
    }

    FinisherOrNewCondition getFonc(){
        return fonc;
    }
}
