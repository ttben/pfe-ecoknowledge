package fr.unice.polytech.ecoknowledge.language.api.interfaces;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;

/**
 * Created by Sébastien on 30/11/2015.
 */
public interface IImprovementFinished {

    public IAndable comparedTo(OLD_PERIOD when);
}
