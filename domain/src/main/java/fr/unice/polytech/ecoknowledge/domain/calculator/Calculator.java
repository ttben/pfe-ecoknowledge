package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;

import java.io.IOException;
import java.util.List;

public class Calculator {

    private Cache cache;
    private Clock clock;

    public Calculator(Cache cache) {
        this.cache = cache;
        this.clock = new Clock();
    }

    public JsonObject evaluate(Goal g) throws IOException {

        // Creating processor to evaluate
        AchievementProcessor ap = new AchievementProcessor(g, cache);
        // Ask evaluation
        g.accept(ap);
        // Get results
        GoalResult gr = ap.getGoalResult();

        // Look for the best badge
        Badge bestBadge = getBestBadge(gr.getLevelResultList());
        System.out.println("Evaluation --> Badge won");

        if(bestBadge != null){
            // Give the badge
            Controller.getInstance().giveBadge(bestBadge, g.getUser().getId().toString());

            // Delete the goal
            Controller.getInstance().deleteGoal(g.getId().toString());

            // Give a new goal
            // TODO
        }

        return gr.toJsonForClient();
    }

    private Badge getBestBadge(List<LevelResult> levelResultList) {

        Badge b = null;
        for(LevelResult lr : levelResultList){
            if(lr.isAchieved())
                b = lr.getLevel().getBadge();
        }
        return b;
    }

	public Clock getClock() {
		return clock;
	}
}