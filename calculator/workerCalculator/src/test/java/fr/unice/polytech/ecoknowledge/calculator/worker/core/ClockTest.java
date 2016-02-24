package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.TimeZone;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class ClockTest {

	@Test
	public void checkDateCreationEurope() {

		Clock c = new Clock();
		DateTime withEurope = c.parseDate("2015-12-01T18:00:00Z");

		assertEquals("Europe/Paris", withEurope.getZone().getID());
		assertEquals(18, withEurope.getHourOfDay());

	}

	@Test
	public void checkDateCreationAmerica() {

		Clock c = new Clock();
		c.setMiddleWareTZ(TimeZone.getTimeZone("America/Los_Angeles"));
		DateTime withAmerica = c.parseDate("2015-12-01T18:00:00Z");


		assertEquals("America/Los_Angeles", withAmerica.getZone().getID());
		Assert.assertEquals(18, withAmerica.getHourOfDay());
	}

}
