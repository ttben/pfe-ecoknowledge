package fr.unice.polytech.ecoknowledge.business.overall;

import business.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.business.Badge;
import fr.unice.polytech.ecoknowledge.business.Challenge;
import fr.unice.polytech.ecoknowledge.business.TimeBox;

import java.util.*;

public class StandardChallenge extends Challenge {

	private Collection<OverallCondition> conditions;

	public StandardChallenge(@JsonProperty("badge") Badge badge, @JsonProperty("lifeSpan") TimeBox timeBox,
							 @JsonProperty("recurrence") String recurrence, @JsonProperty("conditions") List<OverallCondition> overallConditionList) {
		super(badge, timeBox, recurrence);
		this.conditions = overallConditionList;
	}
}