package fr.unice.polytech.ecoknowledge.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.ecoknowledge.language.improve.ImproveChallenge;
import fr.unice.polytech.ecoknowledge.language.overall.StandardChallenge;


@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = ImproveChallenge.class, name = "improve"),
		@JsonSubTypes.Type(value = StandardChallenge.class, name = "overall")}
)
public abstract class Challenge {


	private Badge badge;
	private TimeBox timeSpan;
	private String recurrence;


	@JsonCreator
	public Challenge(@JsonProperty(value = "badge", required = true) Badge badge,
					 @JsonProperty(value = "lifeSpan", required = true) TimeBox timeBox,
					 @JsonProperty(value = "recurrence", required = true) String recurrence) {
		this.badge = badge;
		this.timeSpan = timeBox;
		this.recurrence = recurrence;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public TimeBox getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeBox timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
}