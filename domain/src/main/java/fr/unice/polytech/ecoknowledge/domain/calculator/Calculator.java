package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.google.gson.JsonObject;
import exceptions.GoalNotFoundException;
import exceptions.NotReadableElementException;
import exceptions.NotSavableElementException;
import exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.InvalidGoalTimespanOverChallengeException;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.RecurrenceType;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class Calculator {

    private Cache cache;
    private Clock clock;

    private final Logger logger = LogManager.getLogger(Calculator.class);

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

        System.out.println(goalResult.getCorrectRate());

        // Look for the best badge
        Badge bestBadge = getBestBadge(goalResult.getLevelResultList());

        boolean isOver;

        if(goal.getChallengeDefinition().getRecurrence().getRecurrenceType().equals(RecurrenceType.NONE)){
            isOver = goal.getChallengeDefinition().getLifeSpan().getEnd().isBefore(clock.getTime());
        } else {
            isOver = goal.getTimeSpan().getEnd().isBefore(clock.getTime());
        }

        if(isOver){
            if(bestBadge != null) {
                System.out.println("Evaluation --> Badge won");
                // Give the badge
                Model.getInstance().giveBadge(bestBadge, goal.getUser().getId().toString());
            } else {
                System.out.println("Evalutaion --> Badge lost");
            }
            // Delete the goal
            Model.getInstance().deleteGoal(goal);

            try{
                // Give a new goal
                JsonObject json = new JsonObject();
                json.addProperty("user", goal.getUser().getId().toString());
                json.addProperty("challenge", goal.getChallengeDefinition().getId().toString());

                Model.getInstance().takeChallenge(json, goal.getTimeSpan());
            } catch (InvalidGoalTimespanOverChallengeException itoce){
                logger.info("Can't take challenge again :");
                logger.info(itoce.getMessage());
            }
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