package fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;

public class StandardConditionView implements ConditionView {

	private StandardCondition standardCondition;

	public StandardConditionView(StandardCondition standardCondition) {
		this.standardCondition = standardCondition;
	}

	@Override
	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		String description = this.standardCondition.getExpression().describe();
		description = description.concat(this.standardCondition.getCounter().toString());

		result.addProperty("condition", description);

		return result;
	}


}
