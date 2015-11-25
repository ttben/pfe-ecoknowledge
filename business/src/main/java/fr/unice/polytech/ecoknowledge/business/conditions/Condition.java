package fr.unice.polytech.ecoknowledge.business.conditions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.OverallCondition;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.business.conditions.improve.ImproveCondition;

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

}
