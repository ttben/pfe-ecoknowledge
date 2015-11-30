package fr.unice.polytech.ecoknowledge.language.api.implem;

import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class ComparatorsTest {

    @Test
    public void comparatorLowerThan(){

        ChallengeBuilder cb = Challenge.create("comparatorLT");
        cb
                .dontSend() // Just because it's a test
                .availableFrom(1).to(2)
                .during(2, WEEK)
                .rewards(2)
                .withConditions()
                    .valueOf("Sensor").lowerThan(5)
                .end();

        String comparator = getComparatorForSimpleChallenge(cb.getDescription());
        Assert.assertEquals("<", comparator);

    }

    @Test
    public void comparatorGreaterThan(){

        ChallengeBuilder cb = Challenge.create("comparatorGT");
        cb
                .dontSend() // Just because it's a test
                .availableFrom(1).to(2)
                .during(2, WEEK)
                .rewards(2)
                .withConditions()
                .valueOf("Sensor").greaterThan(5)
                .end();

        String comparator = getComparatorForSimpleChallenge(cb.getDescription());
        Assert.assertEquals(">", comparator);

    }
    @Test
    public void comparatorEqualsTo(){

        ChallengeBuilder cb = Challenge.create("comparatorLT");
        cb
                .dontSend() // Just because it's a test
                .availableFrom(1).to(2)
                .during(2, WEEK)
                .rewards(2)
                .withConditions()
                .valueOf("Sensor").equalsTo(5)
                .end();

        String comparator = getComparatorForSimpleChallenge(cb.getDescription());
        Assert.assertEquals("=", comparator);

    }

    @Test
    public void comparatorDifferentFrom(){

        ChallengeBuilder cb = Challenge.create("comparatorDF");
        cb
                .dontSend() // Just because it's a test
                .availableFrom(1).to(2)
                .during(2, WEEK)
                .rewards(2)
                .withConditions()
                .valueOf("Sensor").differentFrom(5)
                .end();

        String comparator = getComparatorForSimpleChallenge(cb.getDescription());
        Assert.assertEquals("!=", comparator);

    }

    private String getComparatorForSimpleChallenge(JSONObject json){
        JSONArray levels = json.getJSONArray("levels");
        Assert.assertNotNull(levels);

        JSONObject level = levels.getJSONObject(0);
        Assert.assertNotNull(level);

        JSONArray conditions = level.getJSONArray("conditions");
        Assert.assertNotNull(conditions);

        JSONObject condition = conditions.getJSONObject(0);
        Assert.assertNotNull(condition);

        JSONObject expression = condition.getJSONObject("expression");
        return expression.getString("comparator");
    }

}
