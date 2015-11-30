package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sébastien on 26/11/2015.
 */
public class JSONBuilder {
    public static JSONObject parse(ChallengeBuilder cb) {
        JSONObject challenge = new JSONObject();

        challenge.put("name", cb.getName());
        challenge.put("lifeSpan", parsePeriod(cb.getP()));
        challenge.put("recurrence", parseRecurrence(cb));
        challenge.put("levels", createLevels(cb));

        return challenge;
    }

    private static JSONObject parseRecurrence(ChallengeBuilder cb) {
        JSONObject period = new JSONObject();

        period.put("type", cb.getType().toString());
        period.put("unit", cb.getTime());

        return period;
    }

    private static JSONObject parsePeriod(Period p) {
        JSONObject period = new JSONObject();

        period.put("start", getStringFromDateTime(p.getStart()));
        period.put("end", getStringFromDateTime(p.getEnd()));

        return period;
    }

    private static String getStringFromDateTime(DateTime time) {

        Date d = time.toDate();

        SimpleDateFormat day =
                new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat hour =
                new SimpleDateFormat("HH:mm:ss");

        String dayS = day.format(d);
        String hourS = hour.format(d);

        return dayS + "T" + hourS + "Z";
    }


    private static JSONArray createLevels(ChallengeBuilder cb) {
        JSONArray levels = new JSONArray();

        levels.put(createLevel(cb));

        return levels;
    }

    private static JSONObject createLevel(ChallengeBuilder cb) {
        JSONObject level = new JSONObject();

        level.put("name", cb.getName());
        level.put("badge", createBadge(cb));
        level.put("conditions", parseConditions(cb.getConditions()));

        return level;
    }

    private static JSONObject createBadge(ChallengeBuilder cb) {
        JSONObject badge = new JSONObject();

        badge.put("name", cb.getName());
        badge.put("reward", cb.getPoints());
        badge.put("image", "https://drive.google.com/file/d/0B_K7OPwZvrgHaXFUTk94SE1ndHc/view?usp=sharing");

        return badge;
    }

    private static JSONArray parseConditions(List<Condition> conditions) {
        JSONArray conditionsA = new JSONArray();

        for(Condition c : conditions){
            switch (c.getType()){
                case AVERAGE:
                    conditionsA.put(parseOverAllCondition(c));
                    break;
                case VALUE_OF:
                    conditionsA.put(parseStandardCondition(c));
                    break;
            }

        }

        return conditionsA;
    }

    private static JSONObject parseOverAllCondition(Condition c) {
        JSONObject condition = new JSONObject();

        // TODO

        return condition;
    }

    private static JSONObject parseStandardCondition(Condition c) {
        JSONObject condition = new JSONObject();

        condition.put("type", "standard");
        condition.put("expression", parseExpression(c));

        return condition;
    }

    private static JSONObject parseExpression(Condition c) {
        JSONObject expression = new JSONObject();

        JSONObject leftOperand = new JSONObject();
        leftOperand.put("type", "symbolicName");
        leftOperand.put("symbolicName", c.getSensor());

        JSONObject rightOperand = new JSONObject();
        rightOperand.put("type", "value");
        rightOperand.put("value", c.getValue());

        expression.put("leftOperand", leftOperand);
        expression.put("rightOperand", rightOperand);
        expression.put("comparator", c.getComparator());
        expression.put("counter", parseAtLeast(c.getWfv()));

        return expression;
    }

    private static JSONObject parseAtLeast(WaitForValue w) {
        JSONObject counter = new JSONObject();

        if(w == null) {
            counter.put("threshold", 100);
            counter.put("type", AT_LEAST_TYPE.PERCENT.toString());
        } else {
            counter.put("threshold", w.getAtLeast());
            counter.put("type", w.getType().toString());
        }

        return counter;
    }

}

