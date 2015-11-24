package business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Goal {

	private Challenge definition;
	private TimeBox timeSpan;
	private Badge currentBadge;

	@JsonCreator
	public Goal(@JsonProperty("goal")Challenge definition, @JsonProperty("lifeSpan")TimeBox timeSpan, @JsonProperty("badge")Badge currentBadge) {

	}
}