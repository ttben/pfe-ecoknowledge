package fr.unice.polytech.ecoknowledge.language.api.interfaces;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;

/**
 * Created by Sébastien on 27/11/2015.
 */
public interface IDuringable {

	public ILevelable repeatEvery(Integer value, DURATION_TYPE type);

	public ILevelable noRepeat();
}
