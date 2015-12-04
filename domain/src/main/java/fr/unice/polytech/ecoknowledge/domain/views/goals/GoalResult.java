package fr.unice.polytech.ecoknowledge.domain.views.goals;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;

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
		JsonObject result = new JsonObject();

		JsonArray levelsJsonArray = new JsonArray();
		for(LevelResult levelResult : this.levelResultList) {
			JsonObject currentLevelResultDescription = levelResult.toJsonForClient();
			levelsJsonArray.add(currentLevelResultDescription);
		}
		result.add("levels", levelsJsonArray);

		result.addProperty("id", this.goal.getId().toString());
		result.addProperty("name", this.goal.getChallengeDefinition().getName());
		result.addProperty("progressPercent", this.correctRate);
		result.addProperty("timePercent", 20);    // FIXME: 03/12/2015 HARDCODED
		result.addProperty("remaining", "5 jours");    // FIXME: 03/12/2015 HARDCODED
		result.addProperty("startTime", this.goal.getChallengeDefinition().getTimeSpan().getStart().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		result.addProperty("endTime", this.goal.getChallengeDefinition().getTimeSpan().getEnd().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));

		// FIXME: 30/11/2015 unit not used
		switch (this.goal.getChallengeDefinition().getRecurrence().getRecurrenceType()) {
			case DAY:
				result.addProperty("length", "1 jour");
				break;
			case WEEK:
				result.addProperty("length", "1 semaine");
				break;
			case MONTH:
				result.addProperty("length", "1 month");
				break;
			default:
				break;
		}

		/*
		    "id": 3,
    "name": "Magmar",
    "timePercent": 20,
    "progressPercent": 70,
    "startTime":"2015-12-01",
    "endTime":"2015-12-31",
    "length":"1 semaine",
    "remaining":"5 jours",
		 */


		return result;
	}
}
