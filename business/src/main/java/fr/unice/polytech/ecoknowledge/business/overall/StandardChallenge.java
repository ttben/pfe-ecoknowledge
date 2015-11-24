package fr.unice.polytech.ecoknowledge.business.overall;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.business.Badge;
import fr.unice.polytech.ecoknowledge.business.Challenge;
import fr.unice.polytech.ecoknowledge.business.TimeBox;

import java.util.Collection;
import java.util.List;

public class StandardChallenge extends Challenge {

	private Collection<OverallCondition> conditions;

	public StandardChallenge(@JsonProperty(value="badge", required = true) Badge badge,
							 @JsonProperty(value="lifeSpan", required = true) TimeBox timeBox,
							 @JsonProperty(value="recurrence", required = true) String recurrence,
							 @JsonProperty(value="conditions", required = true) List<OverallCondition> overallConditionList) {
		super(badge, timeBox, recurrence);
		this.conditions = overallConditionList;
	}
}