package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;

import java.util.List;

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

        Badge bestBadge = getBestBadge(gr.getLevelResultList());
        System.out.println("Best badge : " + bestBadge);
        if(bestBadge != null){
            //Controller.getInstance().giveBadge(bestBadge, g.getUser().getId().toString());
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