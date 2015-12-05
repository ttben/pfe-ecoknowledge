package fr.unice.polytech.ecoknowledge.domain.model.conditions.improve;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;

public class ImproveCondition implements Condition {

    private final String symbolicName;
    private final String type;
    private TimeBox comparedPeriod;
	private Double threshold;

	@JsonCreator
	public ImproveCondition(@JsonProperty(value = "referencePeriod", required = true) TimeBox comparedPeriod,
							@JsonProperty(value = "threshold", required = true) Double threshold,
                            @JsonProperty(value = "improvementType", required = true) String type,
                            @JsonProperty(value = "symbolicName", required = true) String symbolicName) {
		this.comparedPeriod = comparedPeriod;
		this.threshold = threshold;
        this.type = type;
        this.symbolicName = symbolicName;
	}

    public String getSymbolicName() {
        return symbolicName;
    }

    public String getType() {
        return type;
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

	@Override
	public void accept(GoalVisitor goalVisitor) {
		goalVisitor.visit(this);
	}
}