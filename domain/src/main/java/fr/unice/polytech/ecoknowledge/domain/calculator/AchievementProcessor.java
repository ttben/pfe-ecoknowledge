package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Operand;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class AchievementProcessor implements GoalVisitor {
	
	private Goal goal;
	
	@Override
	public GoalResult visit(Goal goal) {
		this.goal = goal;
		Challenge challengeToEvaluate = goal.getChallengeDefinition();
		return this.visit(challengeToEvaluate);
	}
	
	@Override
	public GoalResult visit(Challenge challenge) {
		/*
		List<Level> levels = challenge.getLevels();

		List<LevelResult> levelResults = new ArrayList<>();
		boolean achieved = true;
		int numberAchieved = 0;

		for (Level level : levels) {
			LevelResult levelResult = evaluateLevel(level);
			levelResults.add(levelResult);
			achieved &= levelResult.isAchieved();
			if (levelResult.isAchieved()) {
				numberAchieved++;
			}
		}

		double correctRate = (numberAchieved * 100) / levelResults.size();

		GoalResult goalResult = new GoalResult(challenge.getName(), achieved, correctRate, levelResults);

		return goalResult;
		*/
		
		return null;
	}

	@Override
	public LevelResult visit(Level level) {
		/*
		List<Condition> conditions = level.getConditionList();

		GoalVisitor goalVisitor = new AchievementProcessor(this.cache, goal);

		List<ConditionResult> conditionResults = new ArrayList<>();
		boolean achieved = true;
		int numberAchieved = 0;

		for (Condition currentCondition : conditions) {
			ConditionResult conditionResult = currentCondition.accept(goalVisitor);

			achieved &= conditionResult.isAchieved();

			if (conditionResult.isAchieved()) {
				numberAchieved++;
			}
		}

		double correctRate = (numberAchieved * 100) / conditionResults.size();

		LevelResult levelResult = new LevelResult(achieved, correctRate, level.getName(), conditionResults);

		return levelResult;
		*/
		return null;
	}

	@Override
	public ConditionResult visit(StandardCondition condition) {
		//	Retrieve symbolic names for condition
		Operand requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getValue().toString();
		String sensorBound = goal.getSensorNameForGivenSymbolicName(symbolicName);

		//	Retrieves values of sensors
		List<Data> data = new ArrayList<>(); // FIXME: 27/11/2015 this.cache.getDataOfSensorBetweenDate(sensorBound, goal.getStart(), goal.getEnd());

		//	Compute evaluation of condition
		int numberOfCorrectValues = 0;

		//	For each data retrieved, do the comparison
		for (Data currentData : data) {
			if (condition.compareWith(currentData.getValue())) {
				numberOfCorrectValues++;
			}
		}

		//	Compute percentage achieved and if achievement
		double correctRate = (numberOfCorrectValues * 100) / data.size();
		boolean achieved = correctRate >= condition.getCounter().getThreshold();

		//	Build conditionResult
		ConditionResult conditionResult = new ConditionResult(achieved, correctRate, condition.getDescription());

		return conditionResult;
	}

	@Override
	public ConditionResult visit(ImproveCondition condition) {
		return null;
	}
}
