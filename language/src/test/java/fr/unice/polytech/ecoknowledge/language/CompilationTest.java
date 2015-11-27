package fr.unice.polytech.ecoknowledge.language;

/**
 * Created by Sébastien on 25/11/2015.
 */

import fr.unice.polytech.ecoknowledge.language.api.implem.Challenge;
import fr.unice.polytech.ecoknowledge.language.api.implem.ChallengeBuilder;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import junit.framework.Assert;
import org.joda.time.IllegalFieldValueException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

public class CompilationTest {

    @Test
    public void structureCheck(){

        Challenge.create("Arbitrary complex challenge")
                .from(2,11).to(1,4,16)
                .during(9, DAY)
                .rewards(30)
                .withConditions()
                    .averageOf("SOMETHING").greaterThan(30).on(WEEK_PERIOD.WEEK_DAYS, DAY_MOMENT.AFTERNOON)
                .and()
                    .valueOf("SOMETHING_ELSE").lowerThan(2)
                .and()
                    .valueOf("SOMETHING_ELSE_AGAIN").lowerThan(30).on(WEEK_PERIOD.WEEK_DAYS).atLeast(3).percent()
                .end();

    }

    @Test
    public void franckWroteThisTest() {

        Challenge.create("bite")
                .from(1).to(2, 3)
                .during(3, DAY).rewards(-10)
                .withConditions()
                .valueOf("bite_length").greaterThan(30)
                .and()
                .averageOf("caca_weight").lowerThan(10)
                .end();
    }

}
