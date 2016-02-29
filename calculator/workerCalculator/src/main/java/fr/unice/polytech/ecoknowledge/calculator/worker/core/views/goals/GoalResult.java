package fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.Calculator;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.ViewForClient;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.RecurrenceType;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
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

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	private long updateTime;

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

		result.addProperty("id", this.getId().toString());
		result.addProperty("name", this.goal.getChallengeDefinition().getName());

		result.addProperty("updateTime", this.updateTime);

		result.addProperty("progressPercent", this.correctRate);

		TimeBox box;
		if (goal.getChallengeDefinition().getRecurrence().getRecurrenceType().equals(RecurrenceType.NONE)) {
			box = goal.getChallengeDefinition().getLifeSpan();
		} else {
			box = goal.getTimeSpan();
		}
		Long remaining = computeRemainingTime(box);

		result.addProperty("remaining", remaining);
		result.addProperty("timePercent", computePercent(box));

		result.addProperty("startTime", this.goal.getTimeSpan().getStart().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		result.addProperty("endTime", this.goal.getTimeSpan().getEnd().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));

		RecurrenceType recurrenceType = this.goal.getChallengeDefinition().getRecurrence().getRecurrenceType();
		result.addProperty("length", recurrenceType.toString());

		// #147
		/*
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
		*/

		return result;
	}

	private Double computePercent(TimeBox timeSpan) {

		if (timeSpan.getEnd().isBefore(Clock.getClock().getTime())) {
			return 100.0;
		}

		Interval between = new Interval(Clock.getClock().getTime(), timeSpan.getEnd());
		long remainingTimeTilTheEnd = between.toDuration().getMillis();

		Interval totalInterval = new Interval(timeSpan.getStart(), timeSpan.getEnd());
		long totalDays = totalInterval.toDuration().getMillis();

		return 100. * (totalDays - remainingTimeTilTheEnd) / totalDays;
	}

	private Long computeRemainingTime(TimeBox lifeSpan) {

		Interval between;
		try {
			between = new Interval(Clock.getClock().getTime(), lifeSpan.getEnd());
		} catch (Throwable t) {
			return null;
		}
		return between.toDuration().getMillis();

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
