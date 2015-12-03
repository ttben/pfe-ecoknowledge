package fr.unice.polytech.ecoknowledge.domain.views.goals;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class GoalResult implements ViewForClient {
	private boolean achieved;
	private double correctRate;
	private List<LevelResult> levelResultList = new ArrayList<>();
	private Goal goal;

	public GoalResult(Goal goal, boolean achieved, double correctRate, List<LevelResult> levelResultList) {
		this.achieved = achieved;
		this.correctRate = correctRate;
		this.levelResultList = levelResultList;
		this.goal = goal;
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


	public JsonObject toJsonForClient() {
		JsonObject result = new GoalView(goal).toJsonForClient();

		// TODO: 30/11/2015  

		return result;
	}
}
