package fr.unice.polytech.ecoknowledge.language.api;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IBuildable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IBuildable, IChallengeable {


    @Override
    public void build() {

    }

    @Override
    public IChallengeable during(Integer value, DURATION_TYPE type) {
        return null;
    }

    @Override
    public IChallengeable isWorth(Integer points) {
        return null;
    }

    @Override
    public IDurationnable from(String date) {
        return null;
    }

    @Override
    public IConditionsable onConditionThat() {
        return null;
    }
}
