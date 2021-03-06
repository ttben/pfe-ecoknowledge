package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.LevelBuilderGettable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovementFinished;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IImprovementPercentable;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class Percentable extends LevelBuilderGettable implements IImprovementPercentable {

	private Improvement i;

	public Percentable(Improvement improvement) {
		this.i = improvement;
	}

	@Override
	public IImprovementFinished percent() {
		ImprovementPeriod ip = new ImprovementPeriod(this);
		return ip;
	}

	Improvement getImprovement() {
		return i;
	}

	@Override
	protected Level getLevel() {
		return i.getLevel();
	}
}
