package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import java.util.*;

/**
 * Created by Sébastien on 02/12/2015.
 */
public enum WeekMoment {
    WEEK_DAYS("weekDays"), WEEK_END("weekEnd"), ALL("all");

    private String days;


    public static WeekMoment fromString(String text) {
        if (text != null) {
            for (WeekMoment w : WeekMoment.values()) {
                if (text.equalsIgnoreCase(w.days)) {
                    return w;
                }
            }
        }
        throw new IllegalArgumentException("Field " + text + " does not exist on weekmoment object");
    }

    WeekMoment(String days) {
        this.days = days;
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getDays(){
        switch (days){
            case ("weekDays"):
               return new AbstractMap.SimpleEntry<Integer, Integer>(1, 5);
            case("weekEnd"):
                return new AbstractMap.SimpleEntry<Integer, Integer>(6, 7);
            default:
                return new AbstractMap.SimpleEntry<Integer, Integer>(1, 7);
        }
    }
}
