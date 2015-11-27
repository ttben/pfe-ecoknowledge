package fr.unice.polytech.ecoknowledge.language.api.interfaces;


import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;

/**
 * Created by Sébastien on 25/11/2015.
 */
public interface ISecondActiveDurationnable {

    public IAtLeastable atLeast(Integer value);

}
