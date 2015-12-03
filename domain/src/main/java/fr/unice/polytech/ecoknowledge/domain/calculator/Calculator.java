package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;

public class Calculator {

	private Cache cache;
    private Clock clock;

    public Calculator(Cache cache) {
		this.cache = cache;
        this.clock = new Clock();
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

        System.out.println(res);

        return res;
    }

    public Clock getClock() {
        return clock;
    }
}