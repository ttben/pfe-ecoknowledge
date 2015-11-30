package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.CounterType;

@JsonDeserialize(using = RecurrenceDeserializer.class)
public class Recurrence {
	private RecurrenceType recurrenceType;
	private Integer unit;

	@JsonCreator
	public Recurrence(@JsonProperty(value = "type", required = true) RecurrenceType recurrenceType,
					  @JsonProperty(value = "unit", required = true) Integer unit) {
		this.recurrenceType = recurrenceType;
		this.unit = unit;
	}

	public RecurrenceType getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(RecurrenceType recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		String res = "";

		switch (recurrenceType) {
			case DAY:
				res = res.concat(" (se lance chaque jour) ");
				break;
			case WEEK:
				res = res.concat(" (se lance chaque semaine) ");
				break;
			case MONTH:
				res = res.concat(" (se lance chaque mois) ");
				break;
			default:
				break;
		}

		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Recurrence)) {
			return false;
		}

		Recurrence recurrence = (Recurrence)obj;

		return recurrenceType.equals(recurrence.recurrenceType)
				&& unit.equals(recurrence.unit);
	}
}
