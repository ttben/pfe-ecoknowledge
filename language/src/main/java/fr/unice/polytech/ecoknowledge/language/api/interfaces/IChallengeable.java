package fr.unice.polytech.ecoknowledge.language.api.interfaces;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IChallengeable {

    public IDurationnable avalaibleFrom(int day);
    public IDurationnable avalaibleFrom(int day, int month);
    public IDurationnable avalaibleFrom(int day, int month, int year);

}
