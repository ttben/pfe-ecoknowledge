package fr.unice.polytech.ecoknowledge.language.api.interfaces;

import fr.unice.polytech.ecoknowledge.language.api.DURATION_TYPE;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IChallengeable extends IBuildable {

    public IChallengeable during(Integer value, DURATION_TYPE type);
    public IChallengeable isWorth(Integer points);
    public IDurationnable from(String date);
    public IConditionsable onConditionThat();

}
