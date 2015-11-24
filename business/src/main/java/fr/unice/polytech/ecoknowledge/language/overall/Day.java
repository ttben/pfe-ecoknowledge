package fr.unice.polytech.ecoknowledge.language.overall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Day {

	private List<Hour> targetHours = new ArrayList<>();

	@JsonCreator
	public Day(@JsonProperty("hours")List<Hour> targetHours) {
		this.targetHours = targetHours;
	}


}