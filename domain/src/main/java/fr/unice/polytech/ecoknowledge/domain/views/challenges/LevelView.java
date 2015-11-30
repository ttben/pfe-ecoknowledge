package fr.unice.polytech.ecoknowledge.domain.views.challenges;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions.ConditionView;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions.ConditionViewFactory;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;

public class LevelView {

	private Level level;

	public LevelView(Level level) {
		this.level = level;
	}

	public JsonObject toJsonForClient() {
		JsonObject result = new JsonObject();

		result.addProperty("name", this.level.getName());

		JsonArray conditionsJsonArray = new JsonArray();
		for(Condition condition : this.level.getConditionList()) {
			ConditionView conditionView = ConditionViewFactory.getView(condition);
			JsonObject currentJsonOfCondition = conditionView.toJsonForClient();
			conditionsJsonArray.add(currentJsonOfCondition);
		}

		result.add("conditions", conditionsJsonArray);

		return result;
	}
}
