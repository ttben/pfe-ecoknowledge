package fr.unice.polytech.ecoknowledge.language.api.interfaces;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IConditionable {

    public  IActiveDurationnable lowerThan(Integer value);
    public IActiveDurationnable greaterThan(Integer value);

}
