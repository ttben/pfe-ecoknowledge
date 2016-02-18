package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;

import java.util.*;

public class User {

	private UUID id;
	private String firstName;
	private String lastName;
	private String picUrl;
	private List<String> goalIDs;
	private List<Badge> badges;
	private Map<String, String> symbolicNameToSensorNameMap = new HashMap<>();

	@JsonCreator
	public User(@JsonProperty(value = "id", required = false) String ID,
				@JsonProperty(value = "firstName", required = true) String firstName,
				@JsonProperty(value = "lastName", required = true) String lastName,
				@JsonProperty(value = "profilePic", required = false) String picUrl,
				@JsonProperty(value = "goals", required = false) List<String> goalIDs,
				@JsonProperty(value = "badges", required = false) List<Badge> badges,
				@JsonProperty(value = "symbolicNameToSensorNameMap", required = true) Map<String, String> symbolicNameToSensorNameMap) {

		this.id = (ID != null && !ID.isEmpty()) ? UUID.fromString(ID) : UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.picUrl = picUrl;
		this.goalIDs = goalIDs;
		this.badges = badges;
		this.symbolicNameToSensorNameMap = symbolicNameToSensorNameMap;
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

	public void setGoalIDs(List<String> goalIDs) {
		this.goalIDs = goalIDs;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}

		User user = (User) obj;

		return user.firstName.equals(firstName) && user.lastName.equals(lastName)
				&& user.symbolicNameToSensorNameMap.equals(symbolicNameToSensorNameMap);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void addBadge(Badge bestBadge) {
		if (badges == null)
			badges = new ArrayList<>();
		badges.add(bestBadge);
	}
}