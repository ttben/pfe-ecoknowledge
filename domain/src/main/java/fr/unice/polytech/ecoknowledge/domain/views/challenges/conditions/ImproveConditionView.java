package fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

public class ImproveConditionView implements ConditionView {

	private ImproveCondition improveCondition;

	public ImproveConditionView(ImproveCondition improveCondition) {
		this.improveCondition = improveCondition;
	}

	@Override
	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		String description = this.improveCondition.getType() + " " + this.improveCondition.getSymbolicName()
				+ " by " + this.improveCondition.getThreshold() + "%";
		result.addProperty("condition", description);

		return result;
	}


}
