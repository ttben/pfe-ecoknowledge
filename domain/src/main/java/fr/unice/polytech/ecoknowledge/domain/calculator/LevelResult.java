package fr.unice.polytech.ecoknowledge.domain.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class LevelResult {
	private boolean achieved;
	private double correctRate;
	private String description;

	private List<ConditionResult> conditionResultList = new ArrayList<>();

	public LevelResult(boolean achieved, double correctRate, String description, List<ConditionResult> conditionResultList) {
		this.achieved = achieved;
		this.correctRate = correctRate;
		this.description = description;
		this.conditionResultList = conditionResultList;
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

	public List<ConditionResult> getConditionResultList() {
		return conditionResultList;
	}

	public void setConditionResultList(List<ConditionResult> conditionResultList) {
		this.conditionResultList = conditionResultList;
	}
}
