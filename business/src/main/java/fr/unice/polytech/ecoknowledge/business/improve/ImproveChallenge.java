package fr.unice.polytech.ecoknowledge.business.improve;

import fr.unice.polytech.ecoknowledge.business.Badge;
import fr.unice.polytech.ecoknowledge.business.Challenge;
import fr.unice.polytech.ecoknowledge.business.TimeBox;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ImproveChallenge extends Challenge {

	private List<ImproveCondition> conditions = new ArrayList<>();

	public ImproveChallenge(@JsonProperty("badge") Badge badge, @JsonProperty("lifeSpan") TimeBox timeBox, @JsonProperty("recurrence") String recurrence, @JsonProperty("conditions")List<ImproveCondition> conditionList) {
		super(badge, timeBox, recurrence);
		this.conditions = conditionList;
	}

	public List<ImproveCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<ImproveCondition> conditions) {
		this.conditions = conditions;
	}
}