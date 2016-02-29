package fr.unice.polytech.ecoknowledge.domain.model.conditions.improve;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.ecoknowledge.domain.model.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.deserializer.ImproveConditionDeserializer;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;

@JsonDeserialize(using = ImproveConditionDeserializer.class)
public class ImproveCondition implements Condition {

	private final String symbolicName;
	private final String improvementType;
	private TimeBox referencePeriod;
	private Double threshold;

	@JsonCreator
	public ImproveCondition(@JsonProperty(value = "referencePeriod", required = true) TimeBox referencePeriod,
							@JsonProperty(value = "threshold", required = true) Double threshold,
							@JsonProperty(value = "improvementType", required = true) String type,
							@JsonProperty(value = "symbolicName", required = true) String symbolicName) {
		this.referencePeriod = referencePeriod;
		this.threshold = threshold;
		this.improvementType = type;
		this.symbolicName = symbolicName;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public String getImprovementType() {
		return improvementType;
	}

	public TimeBox getReferencePeriod() {
		return referencePeriod;
	}

	public void setReferencePeriod(TimeBox referencePeriod) {
		this.referencePeriod = referencePeriod;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	@Override
	public void accept(GoalVisitor goalVisitor) {
		goalVisitor.visit(this);
	}
}