package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.config.AddressReacher;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class GenerationTest {

    JSONObject description;

    @Before
    public void reinit(){
        description = null;
    }

    public void simpleChallengeCreation(){

        ChallengeBuilder cb = Challenge.create("DSL done");
        cb
                .dontSend() // Just because it's a test
                .availableFrom(23,11,2015).to(7,3,2016)
                .during(1, WEEK)
                .rewards(2)
                .withConditions()
                .valueOf("BENNI_RAGE_QUIT").lowerThan(1).atLeast(5).times()
        .end();

        description = cb.getDescription();
    }

    @Test
    public void simpleChallenge(){
        simpleChallengeCreation();

        Assert.assertEquals("oui", description.get("recurrence"));
        Assert.assertNotNull(description.getJSONObject("lifeSpan"));
        Assert.assertEquals("2015-11-23T00:00:00Z", description.getJSONObject("lifeSpan").getString("start"));
        Assert.assertEquals("2016-03-07T23:59:59Z", description.getJSONObject("lifeSpan").getString("end"));
        Assert.assertNotNull(description.getString("name"));

        JSONArray levels = description.getJSONArray("levels");
        Assert.assertNotNull(levels);
        Assert.assertEquals(1, levels.length());

        JSONObject level = levels.getJSONObject(0);

        Assert.assertNotNull(level);

        JSONObject badge = level.getJSONObject("badge");
        JSONArray conditions = level.getJSONArray("conditions");

        Assert.assertNotNull(level.getString("name"));
        Assert.assertNotNull(conditions);
        Assert.assertEquals(1, conditions.length());
        Assert.assertNotNull(badge);

        Assert.assertNotNull(badge.getInt("reward"));
        Assert.assertNotNull(badge.getString("image"));
        Assert.assertNotNull(badge.getString("name"));

        JSONObject condition = conditions.getJSONObject(0);

        Assert.assertEquals("standard", condition.getString("type"));

        JSONObject expression = condition.getJSONObject("expression");

        Assert.assertNotNull(expression);
        Assert.assertNotNull(expression.getString("comparator"));
        Assert.assertNotNull(expression.getJSONObject("leftOperand"));
        Assert.assertNotNull(expression.getJSONObject("leftOperand").getString("symbolicName"));
        Assert.assertNotNull(expression.getJSONObject("leftOperand").getString("type"));
        Assert.assertNotNull(expression.getJSONObject("rightOperand"));
        Assert.assertNotNull(expression.getJSONObject("rightOperand").getString("type"));
        Assert.assertNotNull(expression.getJSONObject("rightOperand").getInt("value"));

    }

    @Ignore
    @Test
    public void sendForReal(){

        ChallengeBuilder cb = Challenge.create("For real");
        cb
                .availableFrom(23,11,2015).to(7,3,2016)
                .during(1, WEEK)
                .rewards(2)
                .withConditions()
                .valueOf("We did it").greaterThan(9000)
                .end();
    }


    @Test
    public void address(){
        Assert.assertNotNull(AddressReacher.getAddress());
    }


}
