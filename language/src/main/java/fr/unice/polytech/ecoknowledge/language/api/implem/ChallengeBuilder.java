package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;
import fr.unice.polytech.ecoknowledge.language.api.util.HTTPCall;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IChallengeable {

    // ------- FIELDS ------- //

    // Configs to sendTo the request
    private static final String path = "challenge";

    // Specific fields of the challenge builder
    private String name;
    private Period p = null;
    private Integer time = null;
    private DURATION_TYPE type = null;
    private Integer points = null;
    private List<Condition> conditions = new ArrayList<>();

    // Output
    private JSONObject description = null;


    // ------- CONSTRUCTOR ------- //

    public ChallengeBuilder(String name){
        this.name = name;
    }


    // ------- API Methods ------- //

    @Override
    public IDurationnable from(int day) {
        reinit();
        Period p = new Period(this, day);
        return p;
    }
    @Override
    public IDurationnable from(int day, int month) {
        reinit();
        Period p = new Period(this, day, month);
        return p;
    }
    @Override
    public IDurationnable from(int day, int month, int year) {
        reinit();
        Period p = new Period(this, day, month, year);
        return p;
    }
    void end(String IPAddress) {
        description = JSONBuilder.parse(this);
        if(IPAddress != null) {
            Response r = HTTPCall.POST(IPAddress, path, description);
            System.out.println(r.getStatus()==200?
                    "Success sending the challenge"
                    :"Challenge Failed : " + r.getStatusInfo());
        }
    }

    // ------- Accessors ------- //

    void addPeriod(Period period) {
        p = period;
    }

    void addCondition(Condition c) {
        conditions.add(c);
    }

    private void reinit(){
        p = null;
        time = null;
        type = null;
        points = null;
        conditions = new ArrayList<>();
        description = null;
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

    JSONObject getDescription() {
        return description;
    }

    void setTime(Integer time) {
        this.time = time;
    }

    void setType(DURATION_TYPE type) {
        this.type = type;
    }

    void setPoints(Integer points) {
        this.points = points;
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

}
