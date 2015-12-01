package fr.unice.polytech.ecoknowledge.language.api.implem;

/**
 * Created by Sébastien on 25/11/2015.
 */
public enum ConditionType {
    AVERAGE("overall"),VALUE_OF("standard");

    String type;

    ConditionType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
