package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public List<Integer> getDays(){
        switch (days){
            case ("weekDays"):
               return Arrays.asList(1,2,3,4,5);
            case("weekEnd"):
                return Arrays.asList(6,7);
            default:
                return Arrays.asList(1,2,3,4,5,6,7);
        }
    }
}
