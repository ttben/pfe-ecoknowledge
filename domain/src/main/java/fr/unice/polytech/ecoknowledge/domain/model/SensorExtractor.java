package fr.unice.polytech.ecoknowledge.domain.model;

import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.SymbolicName;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 23/02/2016.
 */
public class SensorExtractor implements GoalVisitor{
	private final Logger logger = LogManager.getLogger(SensorExtractor.class);

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
		logger.debug("Visiting std condition");
		//	Retrieve symbolic names for condition
		SymbolicName requiredOperand = condition.getRequiredOperand();

		//	Retrieves sensor bound for symbolic names
		String symbolicName = requiredOperand.getSymbolicName().toString();
		logger.debug("Symbolic name needed to evaluate std cdt : " + symbolicName);

		String sensorBound = currentGoal.getSensorNameForGivenSymbolicName(symbolicName);
		logger.debug("Sensor bound to symbolic name " + symbolicName + " --> " + sensorBound);

		if(sensorBound == null) {
			throw new IllegalArgumentException("User could not have taken this goal. It contains a condition that required" +
					" a symbolic name that has no mapping for given user : needed " + symbolicName + " user has : " + currentGoal.getUser().getSymbolicNameToSensorNameMap());
		}

		logger.debug("User has mapping : " + currentGoal.getUser().getSymbolicNameToSensorNameMap());

		SensorNeeds sensorNeeds = new SensorNeeds(sensorBound, currentGoal.getStart().getMillis()/1000, currentGoal.getEnd().getMillis()/1000);

		logger.debug("New sensor needs : " + sensorNeeds);

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
		logger.debug("Merging with existing data for sensor needs : " + newSensorNeedsToAdd);
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
