package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IBuildable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IConditionsable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IBuildable, IChallengeable {

    private Period p = null;
    private Integer time = null;
    private DURATION_TYPE type = null;
    private Integer points = null;
    private List<Condition> conditions = null;

    ChallengeBuilder(){}

    @Override
    public void build() {
        // Just show it for now
        System.out.println(this);
    }

    @Override
    public IChallengeable during(Integer value, DURATION_TYPE type) {
        this.time = value;
        this.type = type;
        return this;
    }

    @Override
    public IChallengeable isWorth(Integer points) {
        this.points = points;
        return this;
    }

    @Override
    public IDurationnable from(String date) {

        Period p = new Period(this, date);
        return p;
    }

    @Override
    public IConditionsable onConditionThat() {
        conditions = new ArrayList<Condition>();
        Conditions c = new Conditions(this);
        return c;
    }


    void addPeriod(Period period) {
        p = period;
    }

    void addCondition(Condition c) { conditions.add(c);}

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
}
