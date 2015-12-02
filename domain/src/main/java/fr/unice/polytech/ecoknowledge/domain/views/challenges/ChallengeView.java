package fr.unice.polytech.ecoknowledge.domain.views.challenges;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.RecurrenceType;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;
import org.joda.time.format.DateTimeFormat;

import static fr.unice.polytech.ecoknowledge.domain.model.RecurrenceType.DAY;

/**
 * Created by Benjamin on 30/11/2015.
 */
public class ChallengeView implements ViewForClient{

	private Challenge challenge;

	public ChallengeView(Challenge challenge) {
		this.challenge = challenge;
	}


	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		result.addProperty("id", this.challenge.getId().toString());
		result.addProperty("name", this.challenge.getName());

		if(this.challenge.getIcon() !=  null) {
			result.addProperty("image", this.challenge.getIcon());
		}

		result.addProperty("startTime", this.challenge.getTimeSpan().getStart().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		result.addProperty("endTime", this.challenge.getTimeSpan().getEnd().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));

		String recurrence = "";

		// FIXME: 30/11/2015 unit not used
		switch (this.challenge.getRecurrence().getRecurrenceType()) {
			case DAY :
				result.addProperty("length", "1 jour");
				break;
			case WEEK:
				result.addProperty("length", "1 semaine");
				break;
			case MONTH:
				result.addProperty("length", "1 month");
				break;
			default:
				break;
		}

		JsonArray levelJson = new JsonArray();
		for(Level level : this.challenge.getLevels()) {
			LevelView levelView = new LevelView(level);
			JsonObject currentJsonOfLevel = levelView.toJsonForClient();
			levelJson.add(currentJsonOfLevel);
		}

		result.add("levels",levelJson);

		return result;
	}
}

