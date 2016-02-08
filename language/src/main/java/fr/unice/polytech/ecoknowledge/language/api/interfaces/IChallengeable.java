package fr.unice.polytech.ecoknowledge.language.api.interfaces;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IChallengeable {

	public IDurationnable availableFrom(int day);

	public IDurationnable availableFrom(int day, int month);

	public IDurationnable availableFrom(int day, int month, int year);

}
