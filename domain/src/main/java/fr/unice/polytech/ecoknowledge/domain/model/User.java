package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;



public class User {

	private UUID id;
	private String name;
	private Collection<String> goalIDs;
	private Collection<Badge> badges;
	private Map<String, String> symbolicNameToSensorNameMap = new HashMap<>();

	@JsonCreator
	public User(@JsonProperty(value = "id", required = false) String ID,
				@JsonProperty(value = "name", required = true) String name,
				@JsonProperty(value = "goals", required = false)List<String> goalIDs,
				@JsonProperty(value = "badges", required = false)List<Badge> badges,
				@JsonProperty(value = "personalMapping", required = true) Map<String,String> personalMapping) {

		this.id = (ID != null && !ID.isEmpty()) ? UUID.fromString(ID) : UUID.randomUUID();
		this.name = name;
		this.goalIDs = goalIDs;
		this.badges = badges;
		this.symbolicNameToSensorNameMap = personalMapping;
	}

	public Map<String, String> getSymbolicNameToSensorNameMap() {
		return symbolicNameToSensorNameMap;
	}

	public void setSymbolicNameToSensorNameMap(Map<String, String> symbolicNameToSensorNameMap) {
		this.symbolicNameToSensorNameMap = symbolicNameToSensorNameMap;
	}

	public Collection<String> getGoalIDs() {
		return goalIDs;
	}

	public void setGoalIDs(Collection<String> goalIDs) {
		this.goalIDs = goalIDs;
	}

	public Collection<Badge> getBadges() {
		return badges;
	}

	public void setBadges(Collection<Badge> badges) {
		this.badges = badges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof User)) {
			return false;
		}

		User user = (User)obj;

		return user.name.equals(name)
				&& user.symbolicNameToSensorNameMap.equals(symbolicNameToSensorNameMap);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}