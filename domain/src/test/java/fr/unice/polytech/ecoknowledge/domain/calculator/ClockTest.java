package fr.unice.polytech.ecoknowledge.domain.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class ClockTest {

    @Test
    public void createDateTime(){

        String dateTime = "2015-12-01T16:00:00Z";
        Clock c = new Clock();
        assertEquals(16, c.createDate(dateTime).getHourOfDay());
        c.setMiddlewareTimeZone("America/Los_Angeles");
        assertEquals(7, c.createDate(dateTime).getHourOfDay());

    }

    @Test
    public void haveFakeTime(){

        String dateTime = "2015-12-01T16:00:00Z";
        Clock c = new Clock();
        c.setTime(dateTime);
        assertEquals(16, c.getTime().getHourOfDay());
        assertEquals(0, c.getTime().getMinuteOfHour());
        assertEquals(0, c.getTime().getSecondOfMinute());

    }

}
