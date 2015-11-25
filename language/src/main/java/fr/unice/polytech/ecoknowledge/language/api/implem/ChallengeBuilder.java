package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IBuildable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IBuildable, IChallengeable {

    private Period p;

    ChallengeBuilder(){}

    @Override
    public void build() {

    }

    @Override
    public IChallengeable during(Integer value, DURATION_TYPE type) {

        return this;
    }

    @Override
    public IChallengeable isWorth(Integer points) {

        return this;
    }

    @Override
    public IDurationnable from(String date) {

        Period p = new Period(this);
        return p;
    }

    @Override
    public IConditionsable onConditionThat() {
        Conditions c = new Conditions(this);
        return c;
    }


    void addPeriod(Period period) {
        p = period;
    }
}
