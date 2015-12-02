package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.ecoknowledge.domain.model.deserializer.TargetTimeDeserializer;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sébastien on 02/12/2015.
 */
@JsonDeserialize(using = TargetTimeDeserializer.class)
public class TimeFilter {

    List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment;
    AbstractMap.SimpleEntry<Integer, Integer> weekMoment;

    @JsonCreator
    public TimeFilter(@JsonProperty(value="hours", required = true) List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment,
                      @JsonProperty(value="days", required = true) AbstractMap.SimpleEntry<Integer, Integer> weekMoment) {
        this.dayMoment = dayMoment;
        this.weekMoment = weekMoment;
    }

    public TimeFilter() {
        dayMoment = Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(0,23));
        weekMoment = new AbstractMap.SimpleEntry<Integer, Integer>(1,7);
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
}
