package fr.unice.polytech.ecoknowledge.domain.views.challenges;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by Benjamin on 30/11/2015.
 */
public class ChallengeView implements ViewForClient {

	private Challenge challenge;

	public ChallengeView(Challenge challenge) {
		this.challenge = challenge;
	}


	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		result.addProperty("id", this.challenge.getId().toString());
		result.addProperty("name", this.challenge.getName());

		if (this.challenge.getImage() != null) {
			result.addProperty("image", this.challenge.getImage());
		}

		result.addProperty("startTime", this.challenge.getLifeSpan().getStart().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
		result.addProperty("endTime", this.challenge.getLifeSpan().getEnd().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));

		String recurrence = "";

		// FIXME: 30/11/2015 unit not used
		switch (this.challenge.getRecurrence().getRecurrenceType()) {
			case DAY:
				result.addProperty("length", "1 jour");
				break;
			case WEEK:
				result.addProperty("length", "1 semaine");
				break;
			case MONTH:
				result.addProperty("length", "1 month");
				break;
			case NONE:
				result.addProperty("length", "ne se repete pas");
			default:
				break;
		}

		JsonArray levelJson = new JsonArray();
		int index = 1;
		for (Level level : this.challenge.getLevels()) {
			LevelView levelView = new LevelView(index++, level);
			JsonObject currentJsonOfLevel = levelView.toJsonForClient();
			levelJson.add(currentJsonOfLevel);
		}

		result.add("levels", levelJson);
		Long remaining = computeRemainingTime(challenge.getLifeSpan());
		if(remaining == null){
			result.addProperty("remaining", "Defi termine");
		} else {
			result.addProperty("remaining", remaining + " jours");
		}
		result.addProperty("image", this.challenge.getImage());
		return result;
	}

	private Long computeRemainingTime(TimeBox lifeSpan) {
		Interval between;
		try {
			between = new Interval(Model.getInstance().getCalculatorClock().getTime(), lifeSpan.getEnd());
		} catch (Throwable t){
			return null;
		}
		return between.toDuration().getStandardDays();
	}
}

