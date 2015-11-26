package fr.unice.polytech.ecoknowledge.domain.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class GoalResult {
	private String name;
	private boolean achieved;
	private double correctRate;
	private List<LevelResult> levelResultList = new ArrayList<>();

	public GoalResult(String name, boolean achieved, double correctRate, List<LevelResult> levelResultList) {
		this.name = name;
		this.achieved = achieved;
		this.correctRate = correctRate;
		this.levelResultList = levelResultList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<LevelResult> getLevelResultList() {
		return levelResultList;
	}

	public void setLevelResultList(List<LevelResult> levelResultList) {
		this.levelResultList = levelResultList;
	}
}
