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

public class Calculator implements ICalculator {

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

		List<ConditionResult> conditionResults = new ArrayList<>();
		boolean achieved = true;
		int numberAchieved = 0;

		for (Condition c : conditions) {
			c.accept(this, goal, conditionResults);
			ConditionResult conditionResult = conditionResults.get(conditionResults.size() - 1);
			achieved &= conditionResult.isAchieved();
			if (conditionResult.isAchieved()) {
				numberAchieved++;
			}
		}

		double correctRate = (numberAchieved * 100) / conditionResults.size();

		LevelResult levelResult = new LevelResult(achieved, correctRate, level.getName(), conditionResults);

		return levelResult;
	}

	public ConditionResult evaluateCondition(StandardCondition condition, Goal goal, List<ConditionResult> conditionResults) {
		//	Retrieve symbolic names for condition
		Operand requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getValue().toString();
		String sensorBound = this.getSensorNameForGivenGoal(symbolicName, goal);

		//	Retrieves values of sensors
		List<Data> data = this.cache.getDataOfSensorBetweenDate(sensorBound, goal.getTimeSpan().getStart(), goal.getTimeSpan().getEnd());

		//	Compute evaluation of condition
		int numberOfCorrectValues = 0;

		for (Data currentData : data) {
			if (condition.compareWith(currentData.getValue())) {
				numberOfCorrectValues++;
			}
		}

		double correctRate = (numberOfCorrectValues * 100) / data.size();
		boolean achieved = correctRate >= condition.getCounter().getThreshold();

		//	Process conditionResult
		ConditionResult conditionResult = new ConditionResult(achieved, correctRate, condition.getDescription());
		conditionResults.add(conditionResult);

		return conditionResult;
	}

	public void evaluateCondition(ImproveCondition condition, Goal goal, List<ConditionResult> conditionResults) {

	}

	private String getSensorNameForGivenGoal(String symbolicName, Goal goal) {
		return goal.getUser().getSymbolicNameToSensorNameMap().get(symbolicName);
	}
}
