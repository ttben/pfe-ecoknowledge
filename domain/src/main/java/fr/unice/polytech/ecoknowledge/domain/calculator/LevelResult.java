package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import org.json.JSONArray;

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
		JsonObject result = new JsonObject();

		result.addProperty("name", level.getName());
		result.addProperty("percent", this.correctRate);

		JsonArray conditions = new JsonArray();
		for(ConditionResult conditionResult : conditionResultList) {
			conditions.add(conditionResult.toJsonForClient());
		}

		result.add("conditions", conditions);

		return result;
	}
}
