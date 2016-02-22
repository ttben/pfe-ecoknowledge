package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotSavableElementException;

/**
 * Created by Benjamin on 21/02/2016.
 */
public class GoalResultHandler {
	private MongoDBConnector bddConnector;

	public void updateGoalResult(GoalResult goalResult) throws NotSavableElementException {
		bddConnector.updateGoalResult(goalResult.toJsonForClient());
	}

	public void store(GoalResult goalResult) {
		JsonObject goalResultJsonDescription = goalResult.toJsonForClient();
		bddConnector.storeResult(goalResultJsonDescription);
	}
}
