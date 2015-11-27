package fr.unice.polytech.ecoknowledge.language.api.interfaces;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IDurationnable {

    public IChallengeable to(int day);
    public IChallengeable to(int day, int month);
    public IChallengeable to(int day, int month, int year);

}
