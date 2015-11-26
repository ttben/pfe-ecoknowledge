package fr.unice.polytech.ecoknowledge.language;

/**
 * Created by Sébastien on 25/11/2015.
 */

import fr.unice.polytech.ecoknowledge.language.api.implem.Challenge;
import org.junit.Ignore;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.*;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD.*;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT.*;

public class CompilationExampleTest {

    @Test
    public void compilationTest(){

        Challenge.create("DSL done")
                .from("23/11/2015").to("7/03/2016")
                .during(1, WEEK)
                .isWorth(2)
                .onConditionThat()
                        .valueOf("BENNI_RAGE_QUIT").lowerThan(1)
                .build();
    }

}
