package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDuringable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.ILevelable;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class During implements IDuringable {

	ChallengeBuilder cb;

	During(ChallengeBuilder cb) {
		this.cb = cb;
	}

	@Override
	public ILevelable during(Integer value, DURATION_TYPE type) {
		cb.setTime(value);
		cb.setType(type);
		return new Level(this);
	}

	ChallengeBuilder getChallengeBuilder() {
		return cb;
	}

}
