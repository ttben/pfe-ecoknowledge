package fr.unice.polytech.ecoknowledge.domain.model.conditions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.ecoknowledge.domain.calculator.ConditionResult;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.OverallCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;

import java.util.List;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")

@JsonSubTypes({
		@JsonSubTypes.Type(value = ImproveCondition.class, name = "improve"),
		@JsonSubTypes.Type(value = OverallCondition.class, name = "overall"),
		@JsonSubTypes.Type(value = StandardCondition.class, name = "standard") }
)
public interface Condition {
	void accept(Calculator t, Goal goal, List<ConditionResult> conditionResults);
}
