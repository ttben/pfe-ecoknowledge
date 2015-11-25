package fr.unice.polytech.ecoknowledge.language.api.interfaces;


import fr.unice.polytech.ecoknowledge.language.api.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.WEEK_PERIOD;


/**
 * Created by Sébastien on 25/11/2015.
 */
public interface ISecondActiveDurationnable {

    public ISecondActiveDurationnableAndAndable andOn(WEEK_PERIOD period, DAY_MOMENT moment);
    public IAtLeastable atLeast(Integer value);

}
