package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.SymbolicName;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class RetrieveRequiredVisitor implements GoalVisitor {

	private Goal goal;

	/**
	 * Symbolic Name : [
	 * {
	 * start : ___
	 * end : ___
	 * }
	 * ]
	 */
	private Map<String, TimeBox> result = new HashMap<>();


	public RetrieveRequiredVisitor(Goal goal) {
		this.goal = goal;
	}


	public Map<String, TimeBox> getResult() {
		return this.result;
	}


	@Override
	public void visit(Goal goal) {

	}

	@Override
	public void visit(Challenge challenge) {

	}

	@Override
	public void visit(Level level) {

	}

	@Override
	public void visit(StandardCondition condition) {
		//	Retrieve symbolic names for condition
		SymbolicName requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getSymbolicName().toString();
		String sensorBound = goal.getSensorNameForGivenSymbolicName(symbolicName);

		TimeBox timeBox = goal.getTimeSpan();
		TimeBox oldTimeBox = this.result.get(sensorBound);

		TimeBox mergedTimeBox = timeBox;
		if (oldTimeBox != null) {
			mergedTimeBox = merge(timeBox, oldTimeBox);
		}

		this.result.put(sensorBound, mergedTimeBox);
	}

	@Override
	// TODO: 27/11/2015
	public void visit(ImproveCondition condition) {

	}


	public TimeBox merge(TimeBox aTimeSpan, TimeBox anotherTimeSpan) {
		DateTime startOfATimeSpan = aTimeSpan.getStart();
		DateTime endOfATimeSpan = aTimeSpan.getStart();

		DateTime startOfAnotherTimeSpan = anotherTimeSpan.getStart();
		DateTime endOfAnotherTimeSpan = anotherTimeSpan.getStart();

		// FIXME: 27/11/2015
		DateTime newStart = (startOfATimeSpan.getMillis() < startOfAnotherTimeSpan.getMillis())
				? startOfATimeSpan
				: startOfAnotherTimeSpan;

		// FIXME: 27/11/2015
		DateTime newEnd = (endOfATimeSpan.getMillis() > endOfAnotherTimeSpan.getMillis())
				? endOfATimeSpan
				: endOfAnotherTimeSpan;

		return new TimeBox(newStart, newEnd);
	}

}
