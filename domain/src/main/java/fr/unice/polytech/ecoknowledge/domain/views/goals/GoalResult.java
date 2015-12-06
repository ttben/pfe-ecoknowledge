package fr.unice.polytech.ecoknowledge.domain.views.goals;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;
import org.joda.time.format.DateTimeFormat;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GoalResult implements ViewForClient {
	private UUID id;
	private boolean achieved;
	private double correctRate;
	private List<LevelResult> levelResultList = new ArrayList<>();
	private Goal goal;

	public GoalResult(Goal goal, boolean achieved, double correctRate, List<LevelResult> levelResultList) {
		this.id = UUID.randomUUID();
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
		result.addProperty("startTime", this.goal.getChallengeDefinition().getLifeSpan().getStart().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		result.addProperty("endTime", this.goal.getChallengeDefinition().getLifeSpan().getEnd().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));

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

		return result;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
