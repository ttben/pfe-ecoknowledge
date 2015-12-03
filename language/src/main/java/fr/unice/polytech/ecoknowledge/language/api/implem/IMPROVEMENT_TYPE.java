package fr.unice.polytech.ecoknowledge.language.api.implem;

/**
 * Created by Sébastien on 01/12/2015.
 */
public enum IMPROVEMENT_TYPE {
	INCREASE("increase"), DECREASE("decrease");

	String type;

	IMPROVEMENT_TYPE(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
