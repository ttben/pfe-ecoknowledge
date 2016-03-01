package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sébastien on 01/03/2016.
 */
public class GEN_ThresholdTest {

    JSONObject description;
    JSONObject description2;

    @Before
    public void createDescription() {
        Challenge c = Challenge.create("Threshold")
                .availableFrom(2, 11, 2016)
                .to(4, 11, 2016)
                .repeatEvery(3, WEEK)
                .addLevel("level")
                .rewards(1)
                .withConditions()
                .valueOf("SENSOR").greaterThan(20).atLeast(50).percentOfTime()
                .endChallenge();

        description = c.getDescription();

        Challenge c2 = Challenge.create("Threshold")
                .availableFrom(2, 11, 2016)
                .to(4, 11, 2016)
                .repeatEvery(3, WEEK)
                .addLevel("level")
                .rewards(1)
                .withConditions()
                .valueOf("SENSOR").greaterThan(20).atLeast(2).times()
                .endChallenge();

        description = c.getDescription();
        description2 = c2.getDescription();
    }

    @Test
    public void checkThreshold(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("counter", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("threshold", Integer.class));

        Object c = JsonSearcher.lookFor(description, wanted);
        Integer j = (Integer) c;

        assertEquals(new Integer(50), j);
    }

    @Test
    public void checkThresholdUnity(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("counter", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("type", String.class));

        Object c = JsonSearcher.lookFor(description, wanted);
        String j = (String) c;

        assertEquals("percent-of-time", j);
    }

    @Test
    public void checkCounter(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("counter", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("threshold", Integer.class));

        Object c = JsonSearcher.lookFor(description2, wanted);
        Integer j = (Integer) c;

        assertEquals(new Integer(2), j);
    }

    @Test
    public void checkCounterUnity(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("counter", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("type", String.class));

        Object c = JsonSearcher.lookFor(description2, wanted);
        System.out.println(c);
        String j = (String) c;

        assertEquals("times", j);
    }
}
