package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;

public class Calculator {

	private Cache cache;

	public Calculator(Cache cache) {
		this.cache = cache;
	}

    public JsonObject evaluate(Goal g){
        AchievementProcessor ap = new AchievementProcessor(g, cache);

        g.accept(ap);

        GoalResult gr = ap.getGoalResult();

        // Just a test for now

        JsonObject res = new JsonObject();
        int i = 0;
        for(LevelResult lr : gr.getLevelResultList())
           res.addProperty("lvl" + i, lr.toJsonForClient().toString());

        return res;
    }


    public void evaluate(String userId, String challengeId) {

    }
}