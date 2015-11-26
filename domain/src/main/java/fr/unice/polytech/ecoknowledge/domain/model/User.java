package fr.unice.polytech.ecoknowledge.domain.model;

import java.util.*;



public class User {

	private Collection<Goal> goals;
	private Collection<Badge> badges;
	private Map<String, String> symbolicNameToSensorNameMap = new HashMap<>();


	public Map<String, String> getSymbolicNameToSensorNameMap() {
		return symbolicNameToSensorNameMap;
	}

	public void setSymbolicNameToSensorNameMap(Map<String, String> symbolicNameToSensorNameMap) {
		this.symbolicNameToSensorNameMap = symbolicNameToSensorNameMap;
	}
}