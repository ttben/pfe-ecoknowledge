package fr.unice.polytech.ecoknowledge.language.api.interfaces;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IConditionable {

    public  IActiveDurationnableAndConditionsable lowerThan(Integer value);
    public IActiveDurationnableAndConditionsable greaterThan(Integer value);

}
