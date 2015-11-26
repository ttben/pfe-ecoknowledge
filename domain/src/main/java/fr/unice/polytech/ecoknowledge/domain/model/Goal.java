package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Goal {

	private Challenge challengeDefinition;
	private TimeBox timeSpan;
	private User user;

	@JsonCreator
	public Goal(@JsonProperty("challenge") Challenge definition,
				@JsonProperty("lifeSpan") TimeBox timeSpan) {

	}

	public Challenge getChallengeDefinition() {
		return challengeDefinition;
	}

	public void setChallengeDefinition(Challenge challengeDefinition) {
		this.challengeDefinition = challengeDefinition;
	}

	public TimeBox getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeBox timeSpan) {
		this.timeSpan = timeSpan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}