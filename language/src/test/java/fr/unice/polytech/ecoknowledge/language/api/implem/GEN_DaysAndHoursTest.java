package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
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
public class GEN_DaysAndHoursTest {

    JSONObject description;
    JSONObject description2;

    @Before
    public void createDescription() {
        Challenge c = Challenge.create("DaysAndHours")
                .availableFrom(2)
                .to(4)
                .repeatEvery(3, WEEK)
                .addLevel("level")
                .rewards(1)
                .withConditions()
                .valueOf("SENSOR").greaterThan(20).on(WEEK_PERIOD.WEEK_DAYS, DAY_MOMENT.MORNING).atLeast(50).percentOfTime()
                .endChallenge();

        description = c.getDescription();

        Challenge c2 = Challenge.create("DaysAndHours")
                .availableFrom(2)
                .to(4)
                .repeatEvery(3, WEEK)
                .addLevel("level")
                .rewards(1)
                .withConditions()
                .valueOf("SENSOR").greaterThan(20).on(WEEK_PERIOD.WEEK_DAYS).atLeast(2).times()
                .endChallenge();

        description = c.getDescription();
        description2 = c2.getDescription();
    }

    @Test
    public void checkDaysOfFirstDescription(){

        ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
        wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
        wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("targetType", JSONObject.class));
        wanted.add(new AbstractMap.SimpleEntry<>("days", String.class));

        
        System.out.println(description);
        Object c = JsonSearcher.lookFor(description, wanted);
    }
}
