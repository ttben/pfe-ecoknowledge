package fr.unice.polytech.ecoknowledge.language.api.implem.enums;

/**
 * Created by Sébastien on 25/11/2015.
 */
public enum DAY_MOMENT {
    MORNING("morning"), AFTERNOON("afternoon"), NIGHT("night"), DAY_TIME("day"), ALL("all");

    String name;

    DAY_MOMENT(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
