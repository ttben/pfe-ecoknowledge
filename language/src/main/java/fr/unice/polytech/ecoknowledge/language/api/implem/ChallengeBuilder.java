package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IBuildable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

import java.util.ArrayList;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IBuildable, IChallengeable {

    private String name;
    private Period p = null;
    private Integer time = null;
    private DURATION_TYPE type = null;
    private Integer points = null;
    private List<Condition> conditions = null;

    private JSONObject description = null;

    ChallengeBuilder(String name){
        this.name = name;
    }

    @Override
    public void end() {
        description = JSONBuilder.parse(this);
    }

    @Override
    public IChallengeable during(Integer value, DURATION_TYPE type) {
        this.time = value;
        this.type = type;
        return this;
    }

    @Override
    public IChallengeable rewards(Integer points) {
        this.points = points;
        return this;
    }

    @Override
    public IDurationnable from(int day) {
        Period p = new Period(this, "" + day);
        return p;
    }
    @Override
    public IDurationnable from(int day, int month) {
        Period p = new Period(this, day + "/" + month);
        return p;
    }
    @Override
    public IDurationnable from(int day, int month, int year) {
        Period p = new Period(this, day + "/" + month + "/" + year);
        return p;
    }


    @Override
    public IConditionsable withConditions() {
        conditions = new ArrayList<>();
        Conditions c = new Conditions(this);
        return c;
    }

    void addPeriod(Period period) {
        p = period;
    }

    public void addCondition(Condition c) {
        conditions.add(c);
    }

    @Override
    public String toString() {
        return "ChallengeBuilder{" +
                "p=" + p +
                ", time=" + time +
                ", type=" + type +
                ", points=" + points +
                ", conditions=" + conditions +
                '}';
    }

    Period getP() {
        return p;
    }

    Integer getTime() {
        return time;
    }

    DURATION_TYPE getType() {
        return type;
    }

    Integer getPoints() {
        return points;
    }

    List<Condition> getConditions() {
        return conditions;
    }

    String getName() {
        return name;
    }

    public JSONObject getDescription() {
        return description;
    }
}
