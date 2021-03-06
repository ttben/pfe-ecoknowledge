package fr.unice.polytech.ecoknowledge.domain.model.challenges;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.model.GoalVisitor;
import fr.unice.polytech.ecoknowledge.domain.model.VisitableComponent;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.Recurrence;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Challenge implements VisitableComponent {

	private UUID id;
	private String name;
	private String image;
	private List<Level> levels = new ArrayList<>();
	private TimeBox lifeSpan;
	private Recurrence recurrence;



	@JsonCreator
	public Challenge(@JsonProperty(value = "id", required = false) String id,
					 @JsonProperty(value = "name", required = true) String name,
					 @JsonProperty(value = "image", required = true) String image,
					 @JsonProperty(value = "levels", required = true) List<Level> levels,
					 @JsonProperty(value = "lifeSpan", required = true) TimeBox lifeSpan,
					 @JsonProperty(value = "recurrence", required = true) Recurrence recurrence) {

		this.id = (id == null || id.isEmpty()) ? UUID.randomUUID() : UUID.fromString(id);
		this.name = name;
		this.image = image;
		this.levels = levels;
		this.lifeSpan = lifeSpan;
		this.recurrence = recurrence;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public TimeBox getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(TimeBox lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	public boolean canTake() {
		System.out.println("Checking if can take a challenge ... Current time :  " + Clock.getClock().getTime() + " Lifespan of challenge :  " + lifeSpan);

		// If we are not in the lifespan yet

		if (Clock.getClock().getTime().isBefore(lifeSpan.getStart()))
			return false;

		// If we are after the lifespan

		if (lifeSpan.getEnd().isBefore(Clock.getClock().getTime()))
			return false;

		// Test time remaining
		DateTime end;
		switch (recurrence.getRecurrenceType()) {
			case DAY:
				return new Interval(
						Clock.getClock().getTime(),
						lifeSpan.getEnd())
						.toDuration().getStandardDays()
						> 0;
			case WEEK:
				end = Clock.getClock().getTime().withDayOfWeek(5);
				return end.isBefore(lifeSpan.getEnd());
			case MONTH:
				end = Clock.getClock().getTime()
						.plusMonths(1)
						.withDayOfMonth(1)
						.minusDays(1);
				return end.isBefore(lifeSpan.getEnd());
			case NONE:
				// Because we only use the lifespan
				return true;
		}

		return false;
	}

	@Override
	public void accept(GoalVisitor goalVisitor) {
		for (VisitableComponent visitableComponent : levels) {
			visitableComponent.accept(goalVisitor);
		}

		goalVisitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Challenge)) {
			return false;
		}

		Challenge challenge = (Challenge) obj;

		return challenge.id.equals(id);
	}
}