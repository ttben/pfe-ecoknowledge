package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Operand;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	private Cache cache;

	public Calculator(Cache cache) {
		this.cache = cache;
	}

	public GoalResult evaluateGoal(Goal goal) {
		Challenge challengeToEvaluate = goal.getChallengeDefinition();
		return this.evaluateChallenge(challengeToEvaluate, goal);
	}

	public GoalResult evaluateChallenge(Challenge challenge, Goal goal) {
		List<Level> levels = challenge.getLevels();

		List<LevelResult> levelResults = new ArrayList<>();
		boolean achieved = true;
		int numberAchieved = 0;

		for (Level level : levels) {
			LevelResult levelResult = evaluateLevel(level, goal);
			levelResults.add(levelResult);
			achieved &= levelResult.isAchieved();
			if (levelResult.isAchieved()) {
				numberAchieved++;
			}
		}

		double correctRate = (numberAchieved * 100) / levelResults.size();

		GoalResult goalResult = new GoalResult(challenge.getName(), achieved, correctRate, levelResults);

		return goalResult;
	}

	public LevelResult evaluateLevel(Level level, Goal goal) {
		List<Condition> conditions = level.getConditionList();

		ConditionVisitor conditionVisitor = new ConditionVisitorImpl(this.cache, goal);

		List<ConditionResult> conditionResults = new ArrayList<>();
		boolean achieved = true;
		int numberAchieved = 0;

		for (Condition currentCondition : conditions) {
			ConditionResult conditionResult = currentCondition.accept(conditionVisitor);

			achieved &= conditionResult.isAchieved();

			if (conditionResult.isAchieved()) {
				numberAchieved++;
			}
		}

		double correctRate = (numberAchieved * 100) / conditionResults.size();

		LevelResult levelResult = new LevelResult(achieved, correctRate, level.getName(), conditionResults);

		return levelResult;
	}
}