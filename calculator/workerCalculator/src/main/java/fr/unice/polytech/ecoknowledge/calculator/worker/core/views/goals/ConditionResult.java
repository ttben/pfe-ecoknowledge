package fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.ViewForClient;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions.ConditionViewFactory;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class ConditionResult implements ViewForClient {
	private boolean achieved;
	private double correctRate;
	private Condition condition;

	public ConditionResult(boolean achieved, double correctRate, Condition condition) {
		this.achieved = achieved;
		this.correctRate = correctRate;
		this.condition = condition;
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

	public double getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(double correctRate) {
		this.correctRate = correctRate;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "Condition " + condition + " achieved at " + correctRate + " so achieved ? " + achieved;
	}

	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		result = ConditionViewFactory.getView(condition).toJsonForClient();
		result.addProperty("percent", this.correctRate);

		return result;
	}
}
