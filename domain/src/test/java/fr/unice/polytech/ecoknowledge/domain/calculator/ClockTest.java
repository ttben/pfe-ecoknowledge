package fr.unice.polytech.ecoknowledge.domain.calculator;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.TimeZone;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class ClockTest {

    @Test
    public void checkDateCreation(){

        Clock c = new Clock();
        DateTime withEurope = c.parseDate("2015-12-01T18:00:00Z");
        c.setMiddleWareTZ(TimeZone.getTimeZone("America/Los_Angeles"));
        DateTime withAmerica = c.parseDate("2015-12-01T18:00:00Z");

        assertEquals(1448989200000L, withEurope.getMillis());
        assertEquals(1449021600000L, withAmerica.getMillis());

    }



}
