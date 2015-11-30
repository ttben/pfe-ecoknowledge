package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class GEN_ConditionTest {

    JSONObject description;

    @Before
    public void createChallenge(){

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
    public void checkOperands(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("expression", JSONObject.class));

        Object e = JsonSearcher.lookFor(description, wanted);
        JSONObject expression = (JSONObject) e;

        assertNotNull(expression.getString("comparator"));
        assertNotNull(expression.getJSONObject("leftOperand"));
        assertNotNull(expression.getJSONObject("leftOperand").getString("symbolicName"));
        assertNotNull(expression.getJSONObject("leftOperand").getString("type"));
        assertNotNull(expression.getJSONObject("rightOperand"));
        assertNotNull(expression.getJSONObject("rightOperand").getString("type"));
        assertNotNull(expression.getJSONObject("rightOperand").getInt("value"));
    }
}
