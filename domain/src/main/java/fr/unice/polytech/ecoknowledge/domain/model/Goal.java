package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;
import org.joda.time.DateTime;

public class Goal implements VisitableComponent {

	private Challenge challengeDefinition;
	private TimeBox timeSpan;
	private User user;

	@JsonCreator
	public Goal(@JsonProperty("challenge") Challenge definition,
				@JsonProperty("lifeSpan") TimeBox timeSpan,
				User user) {
		this.challengeDefinition = definition;
		this.timeSpan = timeSpan;
		this.user = user;
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

	public DateTime getStart() {
		return this.timeSpan.getStart();
	}

	public DateTime getEnd() {
		return this.timeSpan.getEnd();
	}

	public String getSensorNameForGivenSymbolicName(String symbolicName) {
		return this.user.getSymbolicNameToSensorNameMap().get(symbolicName);
	}

	@Override
	public void accept(GoalVisitor goalVisitor) {
		challengeDefinition.accept(goalVisitor);
		goalVisitor.visit(this);
	}
}