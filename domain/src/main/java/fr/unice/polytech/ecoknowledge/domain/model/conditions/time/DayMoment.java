package fr.unice.polytech.ecoknowledge.domain.model.conditions.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sébastien on 02/12/2015.
 */
public enum DayMoment {
    AFTERNOON("afternoon"), MORNING("morning"), DAY("day"), NIGHT("night"), ALL("all");

    private String hours;

    public static DayMoment fromString(String text) {
        if (text != null) {
            for (DayMoment d : DayMoment.values()) {
                if (text.equalsIgnoreCase(d.hours)) {
                    return d;
                }
            }
        }
        throw new IllegalArgumentException("Field " + text + " does not exist on daymoment object");
    }

    DayMoment(String hours) {
        this.hours = hours;
    }

    public List<Integer> getHours(){

        switch (hours){
            case("morning"):
                return Arrays.asList(8,9,10,11);
            case ("afternoon"):
                return Arrays.asList(13,14,15,16,17);
            case("day"):
                return Arrays.asList(8,9,10,11,13,14,15,16,17);
            case("night"):
                return Arrays.asList(21,22,23,0,1,2,3,4,5,6);
            default:
                return Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
        }
    }
}
