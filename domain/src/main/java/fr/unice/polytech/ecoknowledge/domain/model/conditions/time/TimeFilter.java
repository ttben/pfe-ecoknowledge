package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.ecoknowledge.domain.model.deserializer.TargetTimeDeserializer;
import fr.unice.polytech.ecoknowledge.domain.model.serializer.TargetTimeSerializer;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

@JsonSerialize(using = TargetTimeSerializer.class)
@JsonDeserialize(using = TargetTimeDeserializer.class)
public class TimeFilter {

	List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment;
	AbstractMap.SimpleEntry<Integer, Integer> weekMoment;

	private String hoursStr;
	private String daysStr;

	@JsonCreator
	public TimeFilter(@JsonProperty(value = "hours", required = true) List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment,
					  @JsonProperty(value = "days", required = true) AbstractMap.SimpleEntry<Integer, Integer> weekMoment,
					  String hourStr,
					  String daysStr) {
		this.dayMoment = dayMoment;
		this.weekMoment = weekMoment;
		this.hoursStr = hourStr;
		this.daysStr = daysStr;
	}

	public TimeFilter() {
		dayMoment = Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(0, 23));
		weekMoment = new AbstractMap.SimpleEntry<Integer, Integer>(1, 7);
	}

	public List<AbstractMap.SimpleEntry<Integer, Integer>> getDayMoment() {
		return dayMoment;
	}

	public void setDayMoment(List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment) {
		this.dayMoment = dayMoment;
	}

	public AbstractMap.SimpleEntry<Integer, Integer> getWeekMoment() {
		return weekMoment;
	}

	public void setWeekMoment(AbstractMap.SimpleEntry<Integer, Integer> weekMoment) {
		this.weekMoment = weekMoment;
	}

	public String getHoursStr() {
		return hoursStr;
	}

	public String getDaysStr() {
		return daysStr;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TimeFilter)) {
			return false;
		}

		TimeFilter timeFilter = (TimeFilter)obj;
		return dayMoment.equals(timeFilter.dayMoment)
				&& weekMoment.equals(timeFilter.weekMoment);
	}
}
