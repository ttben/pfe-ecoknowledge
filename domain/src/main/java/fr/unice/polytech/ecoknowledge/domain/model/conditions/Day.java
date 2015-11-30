package fr.unice.polytech.ecoknowledge.domain.model.conditions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Day {

	private List<Hour> targetHours = new ArrayList<>();

	@JsonCreator
	public Day(@JsonProperty("hours")List<Hour> targetHours) {
		this.targetHours = targetHours;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Day)) {
			return false;
		}

		Day day = (Day)obj;

		return targetHours.equals(day.targetHours);
	}
}