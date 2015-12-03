package fr.unice.polytech.ecoknowledge.domain.views.goals;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;

/**
 * Created by Benjamin on 30/11/2015.
 */
public class GoalView implements ViewForClient {

	private Goal goal;

	public GoalView(Goal goal) {
		this.goal = goal;
	}

	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		// TODO: 30/11/2015

		return result;
	}
}
