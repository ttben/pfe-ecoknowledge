package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Challenge implements VisitableComponent {

	private UUID id;
	private String name;
	private List<Level> levels = new ArrayList<>();
	private TimeBox timeSpan;
	private String recurrence;

	@JsonCreator
	public Challenge(@JsonProperty(value = "id", required = false) String id,
					 @JsonProperty(value = "name", required = true) String name,
					 @JsonProperty(value = "levels", required = true) List<Level> levels,
					 @JsonProperty(value = "lifeSpan", required = true) TimeBox timeBox,
					 @JsonProperty(value = "recurrence", required = true) String recurrence) {

		this.id = (id == null || id.isEmpty()) ? UUID.randomUUID() : UUID.fromString(id);
		this.name = name;
		this.levels = levels;
		this.timeSpan = timeBox;
		this.recurrence = recurrence;
	}

	public TimeBox getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeBox timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public void accept(GoalVisitor goalVisitor) {
		for(VisitableComponent visitableComponent : levels) {
			visitableComponent.accept(goalVisitor);
		}

		goalVisitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Challenge)) {
			return false;
		}

		Challenge challenge = (Challenge)obj;

		return challenge.id.equals(id)
				&& challenge.name.equals(name)
				&& challenge.levels.equals(levels)
				&& challenge.timeSpan.equals(timeSpan)
				&& challenge.recurrence.equals(recurrence);
	}
}