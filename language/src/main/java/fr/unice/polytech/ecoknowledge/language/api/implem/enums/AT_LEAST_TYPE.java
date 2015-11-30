package fr.unice.polytech.ecoknowledge.language.api.implem.enums;

/**
 * Created by Sébastien on 26/11/2015.
 */
public enum AT_LEAST_TYPE {
    PERCENT("percent-of-time"), TIMES("times");

    String type;

    AT_LEAST_TYPE(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
