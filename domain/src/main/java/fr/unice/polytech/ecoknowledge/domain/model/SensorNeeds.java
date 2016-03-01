package fr.unice.polytech.ecoknowledge.domain.model;

/**
 * Created by Benjamin on 23/02/2016.
 */
public class SensorNeeds {
	private String targetSensor;
	private long dateStart;
	private long dateEnd;

	public SensorNeeds(String targetSensor, long dateStart, long dateEnd) {
		this.targetSensor = targetSensor;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public String getTargetSensor() {
		return targetSensor;
	}

	public void setTargetSensor(String targetSensor) {
		this.targetSensor = targetSensor;
	}

	public long getDateStart() {
		return dateStart;
	}

	public void setDateStart(long dateStart) {
		this.dateStart = dateStart;
	}

	public long getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(long dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		return "[ " + getTargetSensor() + " from " + getDateStart() + " to " + getDateEnd() + "] ";
	}
}
