package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.deserializer.GoalDeserializer;
import fr.unice.polytech.ecoknowledge.domain.model.serializer.GoalSerializer;
import org.joda.time.DateTime;

import java.util.UUID;

@JsonSerialize(using = GoalSerializer.class)
@JsonDeserialize(using = GoalDeserializer.class)
public class Goal implements VisitableComponent {

	private UUID id;
	private Challenge challengeDefinition;
	private TimeBox timeSpan;
	private User user;

	@JsonCreator
	public Goal(@JsonProperty(value = "id", required = false) String id,
				@JsonProperty("challenge") Challenge definition,
				@JsonProperty("lifeSpan") TimeBox timeSpan,
				User user) {

		if(user == null) {
			throw new NullPointerException("User specified is null");
		}

		if(definition == null) {
			throw new NullPointerException("Challenge specified is null");
		}

		this.id = (id != null && !id.isEmpty()) ? UUID.fromString(id) : UUID.randomUUID();
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

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}