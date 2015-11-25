package fr.unice.polytech.ecoknowledge.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Challenge {


	private List<Level> levels = new ArrayList<>();
	private TimeBox timeSpan;
	private String recurrence;


	@JsonCreator
	public Challenge(@JsonProperty(value = "levels", required = true) List<Level> levels,
					 @JsonProperty(value = "lifeSpan", required = true) TimeBox timeBox,
					 @JsonProperty(value = "recurrence", required = true) String recurrence) {
		this.levels = levels;
		this.timeSpan = timeBox;
		this.recurrence = recurrence;
	}

	public TimeBox getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeBox timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
}