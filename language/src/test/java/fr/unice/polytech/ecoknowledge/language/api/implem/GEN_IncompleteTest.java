package fr.unice.polytech.ecoknowledge.language.api.implem;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.*;
import static org.junit.Assert.assertEquals;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class GEN_IncompleteTest {

    @Test
    public void withoutPeriod(){

        ChallengeBuilder cb = Challenge.create("Without period");
        cb
                .dontSend() // Just because it's a test
                .availableFrom(2)
                .to(4)
                .during(3, WEEK)
                .rewards(1)
                .withConditions()
                    .valueOf("SENSOR").greaterThan(2)
                .end();


        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("expression", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("counter", JSONObject.class));

        Object c = JsonSearcher.lookFor(cb.getDescription(), wanted);
        JSONObject j = (JSONObject) c;

        assertEquals(100, j.getInt("threshold"));
        assertEquals(AT_LEAST_TYPE.PERCENT.toString(), j.getString("type"));


    }

}
