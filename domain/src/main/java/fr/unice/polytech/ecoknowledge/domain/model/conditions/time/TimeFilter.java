package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.ecoknowledge.domain.model.deserializer.TargetTimeDeserializer;

import java.util.List;

/**
 * Created by Sébastien on 02/12/2015.
 */
@JsonDeserialize(using = TargetTimeDeserializer.class)
public class TimeFilter {

    List<Integer> dayMoment;
    List<Integer> weekMoment;

    @JsonCreator
    public TimeFilter(@JsonProperty(value="hours", required = true) List<Integer> dayMoment,
                      @JsonProperty(value="days", required = true) List<Integer> weekMoment) {
        this.dayMoment = dayMoment;
        this.weekMoment = weekMoment;
    }

    public List<Integer> getDayMoment() {
        return dayMoment;
    }

    public void setDayMoment(List<Integer> dayMoment) {
        this.dayMoment = dayMoment;
    }

    public List<Integer> getWeekMoment() {
        return weekMoment;
    }

    public void setWeekMoment(List<Integer> weekMoment) {
        this.weekMoment = weekMoment;
    }
}
