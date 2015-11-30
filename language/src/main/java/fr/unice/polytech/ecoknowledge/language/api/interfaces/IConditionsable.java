package fr.unice.polytech.ecoknowledge.language.api.interfaces;


/**
 * Created by Sébastien on 25/11/2015.
 */
public interface IConditionsable {

    public IConditionable averageOf(String sensor);
    public IConditionable valueOf(String sensor);
    public IImprovable improve(String sensor);

}
