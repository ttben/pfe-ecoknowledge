package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.SymbolicName;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals.ConditionResult;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals.LevelResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class AchievementProcessor implements GoalVisitor {

	private final Logger logger = LogManager.getLogger(AchievementProcessor.class);

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
	}

	@Override
	public void visit(Challenge challenge) {

		double percentageAchieved = 0;
		boolean achieved = true;

		for (LevelResult levelResult : currentLevelResult) {
			if (!levelResult.isAchieved()) {
				achieved = false;
			}
			percentageAchieved += levelResult.getCorrectRate();
		}

		double correctRate = percentageAchieved / currentLevelResult.size();

		goalResult = new GoalResult(goal.getId().toString(), goal, achieved, correctRate, currentLevelResult);

		//	Generate creation time
		/*
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		*/

		long todayInSeconds = Calendar.getInstance().getTime().getTime()/1000;
		goalResult.setUpdateTime(todayInSeconds);

		currentLevelResult = new ArrayList<>();
	}

	@Override
	public void visit(Level level) {

		double percentageAchieved = 0;

		for (ConditionResult conditionResult : currentConditionResult) {
			percentageAchieved += conditionResult.getCorrectRate();
		}

		double correctRate = percentageAchieved / currentConditionResult.size();

		boolean achieved = correctRate >= 100.0;

		LevelResult levelResult = new LevelResult(currentLevelResult.size() + 1, achieved, correctRate, currentConditionResult, level);
		currentLevelResult.add(levelResult);

		currentConditionResult = new ArrayList<>();
	}

	@Override
	public void visit(StandardCondition condition) {
		//	Retrieve symbolic names for condition
		SymbolicName requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getSymbolicName().toString();
		String sensorBound = goal.getSensorNameForGivenSymbolicName(symbolicName);

		//System.out.println(goal.getStart() + ", " + goal.getEnd());
		//	Retrieves values of sensors
		List<Data> data = this.cache.getDataOfSensorBetweenDates(sensorBound, goal.getStart(), goal.getEnd());

		logger.info("Visiting STD CDT " + condition.describe() + ", based on " + data);

		//	Compute evaluation of condition
		int numberOfCorrectValues = 0;

		//	For each data retrieved, do the comparison
		for (Data currentData : data) {
			logger.info("Evaluating with data " + data);
			if (condition.compareWith(currentData.getValue())) {
				logger.info("Data correct");
				numberOfCorrectValues++;
			}
		}

		//	Compute percentage achieved and if achievement
		double correctRate = (data.size() > 0) ? (numberOfCorrectValues * 100) / data.size() : 0;

		double achievedRate = 0.0;

		switch (condition.getCounter().getType()) {
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

		achievedRate = (achievedRate > 100)?100:achievedRate;

		//	Build conditionResult
		ConditionResult conditionResult = new ConditionResult(achieved, achievedRate, condition);

		currentConditionResult.add(conditionResult);
	}

	@Override
	public void visit(ImproveCondition condition) {

		//	Retrieves sensor bound for symbolic names
		String symbolicName = condition.getSymbolicName();
		String sensorBound = goal.getSensorNameForGivenSymbolicName(symbolicName);

		System.out.println(condition.getReferencePeriod().getStart());
		System.out.println(condition.getReferencePeriod().getEnd());
		System.out.println(goal.getStart());
		System.out.println(goal.getEnd());

		// Retrieve old values of sensor
		List<Data> oldData = this.cache.getDataOfSensorBetweenDates(sensorBound,
				condition.getReferencePeriod().getStart(), condition.getReferencePeriod().getEnd());

		List<Data> newData = this.cache.getDataOfSensorBetweenDates(sensorBound,
				goal.getStart(), goal.getEnd());

		// If we don't have the good data
		if (oldData == null || newData == null || oldData.isEmpty() || newData.isEmpty()) {
			currentConditionResult.add(new ConditionResult(false, 0.0, condition));
			System.out.println("oldData : " + oldData);
			System.out.println("newData : " + newData);
			return;
		}

		System.out.println(oldData);
		System.out.println(newData);
		double oldSum = 0;
		for (Data d : oldData)
			oldSum += d.getValue();

		double newSem = 0;
		for (Data d : newData)
			newSem += d.getValue();

		double oldAverage = oldSum / oldData.size();
		double newAverage = newSem / newData.size();

		System.out.println("oldaverage : " + oldAverage);
		System.out.println("newaverage : " + newAverage);

		double threshold = condition.getThreshold();

		double achievedRate = 0.0;

		String improveType = condition.getImprovementType();
		switch (improveType) {
			case "increase":
				if (newAverage < oldAverage) achievedRate = 0;
				else achievedRate = 100 * ((newAverage - oldAverage) / oldAverage) * 100 / threshold;
				if (achievedRate > 100) achievedRate = 100;
				break;
			case "decrease":
				if (newAverage > oldAverage) achievedRate = 0;
				else achievedRate = 100 * ((oldAverage - newAverage) / oldAverage) * 100 / threshold;
				if (achievedRate > 100) achievedRate = 100;
		}
		boolean achieved = achievedRate == 1;
		System.out.println("rate : " + achievedRate);
		ConditionResult conditionResult = new ConditionResult(achieved, achievedRate, condition);
		currentConditionResult.add(conditionResult);
	}

	public GoalResult getGoalResult() {
		return goalResult;
	}
}
