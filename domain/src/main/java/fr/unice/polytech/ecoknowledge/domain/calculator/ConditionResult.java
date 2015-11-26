package fr.unice.polytech.ecoknowledge.domain.calculator;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class ConditionResult {
	private boolean achieved;
	private double correctRate;
	private String description;

	public ConditionResult(boolean achieved, double correctRate, String description) {
		this.achieved = achieved;
		this.correctRate = correctRate;
		this.description = description;
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

	public double getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(double correctRate) {
		this.correctRate = correctRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
