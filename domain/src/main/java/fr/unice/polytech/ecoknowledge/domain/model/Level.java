package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;

import java.util.ArrayList;
import java.util.List;

public class Level implements VisitableComponent{
	private List<Condition> conditionList = new ArrayList<>();
	private String name;
	private Badge badge;

	@JsonCreator
	public Level(@JsonProperty(value = "conditions", required = true) List<Condition> conditions,
				 @JsonProperty(value = "name", required = true) String name,
				 @JsonProperty(value = "badge", required = true) Badge badge) {
		this.conditionList = conditions;
		this.name = name;
		this.badge = badge;
	}

	public List<Condition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<Condition> conditionList) {
		this.conditionList = conditionList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	@Override
	public void accept(GoalVisitor goalVisitor) {
		for(VisitableComponent visitableComponent : conditionList) {
			visitableComponent.accept(goalVisitor);
		}

		goalVisitor.visit(this);
	}
}