package fr.unice.polytech.ecoknowledge.language;

/**
 * Created by Sébastien on 25/11/2015.
 */

import fr.unice.polytech.ecoknowledge.language.api.Challenge;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.DURATION_TYPE.*;
import static fr.unice.polytech.ecoknowledge.language.api.WEEK_PERIOD.*;
import static fr.unice.polytech.ecoknowledge.language.api.DAY_MOMENT.*;

public class CompilationExampleTest {

    @Test
    public void compilationTest(){

        Challenge.create("How to heat")
                .from("1/11").to("1/12")
                .during(2, WEEK)
                .isWorth(1000)
                .onConditionThat()
                .averageOf("TEMP_AMB").lowerThan(25).on(WEEK_DAYS, MORNING).andOn(WEEK_DAYS, AFTERNOON)
                .and()
                .averageOf("TEMP_AMB").greaterThan(15).on(WEEK_DAYS, MORNING).andOn(WEEK_DAYS, AFTERNOON)
                .and()
                .valueOf("TEMP_AMB").lowerThan(20).on(WEEK_DAYS, NIGHT).atLeast(80).percent()
                .build();

    }

}
