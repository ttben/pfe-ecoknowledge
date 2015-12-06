package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.data.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;
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

    public GoalResult evaluate(Goal goal) throws IOException, GoalNotFoundException, UserNotFoundException, NotReadableElementException, NotSavableElementException {

        // Creating processor to evaluate
        AchievementProcessor achievementProcessor = new AchievementProcessor(goal, cache);

        // Ask evaluation
        goal.accept(achievementProcessor);

        // Get results
        GoalResult goalResult = achievementProcessor.getGoalResult();

        // Look for the best badge
        Badge bestBadge = getBestBadge(goalResult.getLevelResultList());

        boolean isOver = goal.getTimeSpan().getEnd().isBefore(clock.getTime());

        if(bestBadge != null && isOver){
            System.out.println("Evaluation --> Badge won");
            // Give the badge
            Model.getInstance().giveBadge(bestBadge, goal.getUser().getId().toString());

            // Delete the goal
            Model.getInstance().deleteGoal(goal);

            // Give a new goal
            JsonObject json = new JsonObject();
            json.addProperty("user", goal.getUser().getId().toString());
            json.addProperty("challenge", goal.getChallengeDefinition().getId().toString());

            Model.getInstance().takeChallenge(json, goal.getTimeSpan());
        }

        return goalResult;
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