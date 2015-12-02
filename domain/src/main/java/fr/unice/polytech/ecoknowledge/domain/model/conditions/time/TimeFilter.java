package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sébastien on 02/12/2015.
 */
public class TimeFilter {

    String hours;
    String days;

    @JsonCreator
    public TimeFilter(@JsonProperty(value="hours", required = true) String hours,
                      @JsonProperty(value="days", required = true) String days) {
        this.hours = hours;
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
