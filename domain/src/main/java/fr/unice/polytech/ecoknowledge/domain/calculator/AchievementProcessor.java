package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Operand;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;
import fr.unice.polytech.ecoknowledge.domain.views.goals.ConditionResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class AchievementProcessor implements GoalVisitor {

	private final Cache cache;
	private Goal goal;

	private List<ConditionResult> currentConditionResult = new ArrayList<>();
	private List<LevelResult> currentLevelResult = new ArrayList<>();
	private GoalResult goalResult;

	public AchievementProcessor(Goal goal, Cache cache) {
		this.goal = goal;
		this.cache = cache;
	}

	@Override
	public void visit(Goal goal) {
		this.goal = goal;
		Challenge challengeToEvaluate = goal.getChallengeDefinition();
	}

	@Override
	public void visit(Challenge challenge) {

		double percentageAchieved = 0;
		boolean achieved = true;

		for(LevelResult levelResult : currentLevelResult) {
			if(!levelResult.isAchieved()) {
				achieved = false;
				percentageAchieved += levelResult.getCorrectRate();
			}
		}

		double correctRate = percentageAchieved / currentLevelResult.size();

		goalResult = new GoalResult(goal, achieved, correctRate, currentLevelResult);
		currentLevelResult = new ArrayList<>();
	}

	@Override
	public void visit(Level level) {
		double percentageAchieved = 0;

		for(ConditionResult conditionResult : currentConditionResult) {
			percentageAchieved += conditionResult.getCorrectRate();
		}

		double correctRate = percentageAchieved / currentConditionResult.size();

		boolean achieved = correctRate >= 100.0;

		LevelResult levelResult = new LevelResult(achieved, correctRate, currentConditionResult, level);
		currentLevelResult.add(levelResult);

		currentConditionResult = new ArrayList<>();
	}

	@Override
	public void visit(StandardCondition condition) {

		//	Retrieve symbolic names for condition
		Operand requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getSymbolicName().toString();
		String sensorBound = goal.getSensorNameForGivenSymbolicName(symbolicName);

		//	Retrieves values of sensors
		List<Data> data = this.cache.getDataOfSensorBetweenDate(sensorBound, goal.getStart(), goal.getEnd());

		//	Compute evaluation of condition
		int numberOfCorrectValues = 0;

		//	For each data retrieved, do the comparison
		for (Data currentData : data) {
			if (condition.compareWith(currentData.getValue())) {
				numberOfCorrectValues++;
			}
		}

		//	Compute percentage achieved and if achievement
		double correctRate =(data.size() > 0) ? (numberOfCorrectValues * 100) / data.size() : 0;

		double achievedRate = 0.0;

		switch(condition.getCounter().getCounterType()) {
			case PERCENT_OF_TIME:
				achievedRate = (correctRate * 100) / condition.getCounter().getThreshold();
				break;
			case TIMES:
				achievedRate = (numberOfCorrectValues * 100) / condition.getCounter().getThreshold();
				break;
			default:
				break;
		}

		boolean achieved = achievedRate >= 100.0;

		//	Build conditionResult
		ConditionResult conditionResult = new ConditionResult(achieved, achievedRate, condition);

		currentConditionResult.add(conditionResult);
	}

	// TODO: 27/11/2015  
	@Override
	public void visit(ImproveCondition condition) {
	}

	public GoalResult getGoalResult() {
		return goalResult;
	}
}
