package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.ILevelable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IRewardable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class Level implements ILevelable {

    private During d;
    private String name;
    private Integer points = null;
    private List<Condition> conditions = new ArrayList<>();
    private List<Improvement> improvements = new ArrayList<>();

    public Level(During during) {
        this.d = during;
        d.getChallengeBuilder().addLevel(this);
    }

    @Override
    public IRewardable atLevel(String levelName) {
        this.name = levelName;
        Rewards r = new Rewards(this);
        return r;
    }

    IRewardable newLevel(String levelName){
        Level l = new Level(d);
        l.name = levelName;
        Rewards r = new Rewards(l);
        return r;
    }

    void setPoints(Integer points) {
        this.points = points;
    }

    void addCondition(Condition c) {
        conditions.add(c);
    }

    void addImprovement(Improvement improvement) {
        improvements.add(improvement);
    }

    @Override
    public void end() {
        d.getChallengeBuilder().end();
    }

    String getName() {
        return name;
    }

    List<Condition> getConditions() {
        return conditions;
    }

    List<Improvement> getImprovements() {
        return improvements;
    }

    Integer getPoints() {
        return points;
    }
}
