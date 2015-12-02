package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class GEN_MultipleLevels {

    JSONObject description;

    @Before
    public void createChallenge(){

        ChallengeBuilder cb = Challenge.create("La reine des neiges");
            cb.dontSend() // Just because it's a test
                .availableFrom(21, 12, 2015).to(21, 3, 2016)
                    .during(1, DURATION_TYPE.MONTH)
                    .atLevel("Sven")
                        .rewards(50)
                        .withConditions()
                            .averageOf("TEMP_SENS").lowerThan(20)
                                .on(WEEK_PERIOD.WEEK_DAYS, DAY_MOMENT.DAY_TIME)
                        .and()
                            .valueOf("TEMP_SENS").lowerThan(18)
                                .on(WEEK_PERIOD.WEEK_END)
                    .atLevel("Elsa")
                        .withImage("http://emea.lum.dolimg.com/v1/images/6d7454cea6644379adc7e529c5790a28078a2823.jpeg?region=0,0,450,450")
                        .rewards(100)
                        .withConditions()
                            .averageOf("TEMP_SENS").lowerThan(18)
            .end();
        description = cb.getDescription();
    }

    @Test
    public void checkImageOfLevel1(){


        System.out.println((description));
        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(1, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("badge", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("image", String.class));

        Object i = JsonSearcher.lookFor(description, wanted);
        String image = (String) i;

        assertEquals("http://emea.lum.dolimg.com/v1/images/6d7454cea6644379adc7e529c5790a28078a2823.jpeg?region=0,0,450,450",
                image);
    }

    @Test
    public void checkConditionsLevel1(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));

        Object c = JsonSearcher.lookFor(description, wanted);
        JSONArray conditions = (JSONArray) c;

        assertEquals(2, ((JSONArray) conditions).length());
    }

    @Test
    public void checkConditionsLevel2(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(1, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));

        Object c = JsonSearcher.lookFor(description, wanted);
        JSONArray conditions = (JSONArray) c;

        assertEquals(1, ((JSONArray) conditions).length());
    }
}
