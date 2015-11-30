package fr.unice.polytech.ecoknowledge.domain.views.goals;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.LevelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class LevelResult {
	private boolean achieved;
	private double correctRate;
	private Level level;

	private List<ConditionResult> conditionResultList = new ArrayList<>();

	public LevelResult(boolean achieved, double correctRate, List<ConditionResult> conditionResultList, Level level) {
		this.achieved = achieved;
		this.correctRate = correctRate;
		this.level = level;
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<ConditionResult> getConditionResultList() {
		return conditionResultList;
	}

	public void setConditionResultList(List<ConditionResult> conditionResultList) {
		this.conditionResultList = conditionResultList;
	}

	public JsonObject toJsonForClient() {
		JsonObject result = new LevelView(level).toJsonForClient();
		result.addProperty("percent", this.correctRate);

		return result;
	}
}
