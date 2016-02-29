package fr.unice.polytech.ecoknowledge.domain.model;

import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.SymbolicName;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 23/02/2016.
 */
public class SensorExtractor implements GoalVisitor{
	List<SensorNeeds> sensorNeedsList = new ArrayList<>();

	private Goal currentGoal;

	public List<SensorNeeds> getSensorNeedsList() {
		return sensorNeedsList;
	}

	public SensorExtractor(Goal goal) {
		currentGoal = goal;
	}

	@Override
	public void visit(Goal goal) {}

	@Override
	public void visit(Challenge challenge) {	}

	@Override
	public void visit(Level level) {	}

	@Override
	public void visit(StandardCondition condition) {
		//	Retrieve symbolic names for condition
		SymbolicName requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getSymbolicName().toString();
		String sensorBound = currentGoal.getSensorNameForGivenSymbolicName(symbolicName);

		SensorNeeds sensorNeeds = new SensorNeeds(sensorBound, currentGoal.getStart().getMillis()/1000, currentGoal.getEnd().getMillis()/1000);
		mergeWithExistingNeeds(sensorNeeds);
	}

	@Override
	public void visit(ImproveCondition condition) {
		//	Retrieves sensor bound for symbolic names
		String symbolicName = condition.getSymbolicName();
		String sensorBound = currentGoal.getSensorNameForGivenSymbolicName(symbolicName);

		SensorNeeds sensorNeeds = new SensorNeeds(sensorBound, condition.getReferencePeriod().getStart().getMillis()/1000, currentGoal.getEnd().getMillis()/1000);
		mergeWithExistingNeeds(sensorNeeds);
	}

	private void mergeWithExistingNeeds(SensorNeeds newSensorNeedsToAdd) {
		for(int i = 0 ; i < this.sensorNeedsList.size() ; i ++) {
			SensorNeeds currentSensorNeeds = this.sensorNeedsList.get(i);

			if(currentSensorNeeds.getTargetSensor().equals(newSensorNeedsToAdd.getTargetSensor())) {

				long mergedDateStart = (newSensorNeedsToAdd.getDateStart() < currentSensorNeeds.getDateStart()) ?
						newSensorNeedsToAdd.getDateStart() : currentSensorNeeds.getDateStart();

				long mergedDateEnd = (newSensorNeedsToAdd.getDateEnd() > currentSensorNeeds.getDateEnd()) ?
						newSensorNeedsToAdd.getDateEnd() : currentSensorNeeds.getDateEnd();

				SensorNeeds mergedSensorNeeds = new SensorNeeds(newSensorNeedsToAdd.getTargetSensor(), mergedDateStart, mergedDateEnd);
				this.sensorNeedsList.remove(i);
				this.sensorNeedsList.add(i, mergedSensorNeeds);
				return;
			}
		}

		this.sensorNeedsList.add(newSensorNeedsToAdd);
	}

}
