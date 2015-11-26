package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Operand;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class ConditionVisitorImpl implements ConditionVisitor {

	private Cache cache;

	private Goal goal;

	public ConditionVisitorImpl(Cache cache, Goal goal) {
		this.cache = cache;
		this.goal = goal;
	}

	public ConditionResult evaluateCondition(StandardCondition condition) {
		//	Retrieve symbolic names for condition
		Operand requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getValue().toString();
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
		double correctRate = (numberOfCorrectValues * 100) / data.size();
		boolean achieved = correctRate >= condition.getCounter().getThreshold();

		//	Build conditionResult
		ConditionResult conditionResult = new ConditionResult(achieved, correctRate, condition.getDescription());

		return conditionResult;
	}

	public ConditionResult evaluateCondition(ImproveCondition condition) {
		return null;
	}
}
