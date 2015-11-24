package fr.unice.polytech.ecoknowledge.business.improve;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.business.Badge;
import fr.unice.polytech.ecoknowledge.business.Challenge;
import fr.unice.polytech.ecoknowledge.business.TimeBox;

import java.util.ArrayList;
import java.util.List;

public class ImproveChallenge extends Challenge {

	private List<ImproveCondition> conditions = new ArrayList<>();

	public ImproveChallenge(@JsonProperty(value = "badge", required = true) Badge badge,
							@JsonProperty(value = "lifeSpan", required = true) TimeBox timeBox,
							@JsonProperty(value = "recurrence", required = true) String recurrence,
							@JsonProperty(value = "conditions", required = true) List<ImproveCondition> conditionList) {
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