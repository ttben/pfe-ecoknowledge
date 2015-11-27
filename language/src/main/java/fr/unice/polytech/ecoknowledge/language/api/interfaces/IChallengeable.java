package fr.unice.polytech.ecoknowledge.language.api.interfaces;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IChallengeable extends IBuildable {

    public IChallengeable during(Integer value, DURATION_TYPE type);
    public IChallengeable rewards(Integer points);
    public IDurationnable from(int day);
    public IDurationnable from(int day, int month);
    public IDurationnable from(int day, int month, int year);
    public IConditionsable withConditions();

}
