package fr.unice.polytech.ecoknowledge.language.api.interfaces;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IDurationnable {

    public IDuringable to(int day);
    public IDuringable to(int day, int month);
    public IDuringable to(int day, int month, int year);

}
