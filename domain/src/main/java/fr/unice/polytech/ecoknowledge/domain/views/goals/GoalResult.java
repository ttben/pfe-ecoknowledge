package fr.unice.polytech.ecoknowledge.domain.views.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.time.RecurrenceType;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;
import org.joda.time.Interval;
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


	@JsonCreator
	public GoalResult(@JsonProperty(value = "id", required = false) String id,
					  Goal goal,
					  @JsonProperty(value = "achieved", required = true) boolean achieved,
					  @JsonProperty(value = "correctRate", required = true) double correctRate,
					  @JsonProperty(value = "levelResultList", required = true) List<LevelResult> levelResultList) {
		this.goal = goal;
		if (goal != null) {
			this.id = goal.getId();
		}
		if (this.id == null)
			this.id = (id != null && !id.isEmpty()) ? UUID.fromString(id) : UUID.randomUUID();
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
		for (LevelResult levelResult : this.levelResultList) {
			JsonObject currentLevelResultDescription = levelResult.toJsonForClient();
			levelsJsonArray.add(currentLevelResultDescription);
		}
		result.add("levels", levelsJsonArray);

		result.addProperty("id", this.goal.getId().toString());
		result.addProperty("name", this.goal.getChallengeDefinition().getName());
		result.addProperty("progressPercent", this.correctRate);

		TimeBox box;
		if (goal.getChallengeDefinition().getRecurrence().getRecurrenceType().equals(RecurrenceType.NONE)) {
			box = goal.getChallengeDefinition().getLifeSpan();
		} else {
			box = goal.getTimeSpan();
		}
		Long remaining = computeRemainingTime(box);

		result.addProperty("remaining", remaining + " jours");
		result.addProperty("timePercent", computePercent(box));


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
			case NONE:
				result.addProperty("length", "ne se repete pas");
			default:
				break;
		}

		return result;
	}

	private Double computePercent(TimeBox timeSpan) {

		if (timeSpan.getEnd().isBefore(Model.getInstance().getCalculatorClock().getTime()))
			return 100.0;
		Interval between = new Interval(Model.getInstance().getCalculatorClock().getTime(), timeSpan.getEnd());
		long days = between.toDuration().getStandardDays() + 1;

		Interval totalInterval = new Interval(timeSpan.getStart(), timeSpan.getEnd());
		long totalDays = totalInterval.toDuration().getStandardDays() + 1;

		return 100. * (totalDays - days) / totalDays;
	}

	private Long computeRemainingTime(TimeBox lifeSpan) {// FIXME: 09/12/2015 DUPLIQUE DANS CHALLENGE VIEW
		Interval between;
		try {
			between = new Interval(Model.getInstance().getCalculatorClock().getTime(), lifeSpan.getEnd());
		} catch (Throwable t) {
			return null;
		}
		return between.toDuration().getStandardDays();
	}

	public Goal getGoal() {
		return goal;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
