package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Period extends ChallengeBuilder implements IDurationnable {

    private ChallengeBuilder cb;

    Period(ChallengeBuilder challengeBuilder) {
        cb = challengeBuilder;
        challengeBuilder.addPeriod(this);
    }

    @Override
    public IChallengeable to(String date) {
        return cb;
    }
}
