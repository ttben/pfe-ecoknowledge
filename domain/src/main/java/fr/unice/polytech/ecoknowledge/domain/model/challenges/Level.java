package fr.unice.polytech.ecoknowledge.domain.model.challenges;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.model.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.VisitableComponent;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;

import java.util.ArrayList;
import java.util.List;

public class Level implements VisitableComponent {
	private List<Condition> conditions = new ArrayList<>();
	private String name;
	private Badge badge;

	@JsonCreator
	public Level(@JsonProperty(value = "conditions", required = true) List<Condition> conditions,
				 @JsonProperty(value = "name", required = true) String name,
				 @JsonProperty(value = "badge", required = true) Badge badge) {
		this.conditions = conditions;
		this.name = name;
		this.badge = badge;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
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
		for (VisitableComponent visitableComponent : conditions) {
			visitableComponent.accept(goalVisitor);
		}

		goalVisitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Level)) {
			return false;
		}

		Level level = (Level) obj;

		return conditions.equals(level.conditions)
				&& name.equals(level.name)
				&& badge.equals(level.badge);
	}
}