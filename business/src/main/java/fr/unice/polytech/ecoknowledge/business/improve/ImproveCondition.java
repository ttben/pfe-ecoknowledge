package fr.unice.polytech.ecoknowledge.business.improve;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.business.TimeBox;

public class ImproveCondition {

	private TimeBox comparedPeriod;
	private Double threshold;

	@JsonCreator
	public ImproveCondition(@JsonProperty(value = "referencePeriod", required = true) TimeBox comparedPeriod,
							@JsonProperty(value = "threshold", required = true) Double threshold) {
		this.comparedPeriod = comparedPeriod;
		this.threshold = threshold;
	}

	public TimeBox getComparedPeriod() {
		return comparedPeriod;
	}

	public void setComparedPeriod(TimeBox comparedPeriod) {
		this.comparedPeriod = comparedPeriod;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
}