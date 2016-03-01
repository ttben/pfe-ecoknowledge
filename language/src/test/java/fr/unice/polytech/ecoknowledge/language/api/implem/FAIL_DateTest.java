package fr.unice.polytech.ecoknowledge.language.api.implem;

import org.joda.time.IllegalFieldValueException;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class FAIL_DateTest {

	@Test
	public void dateFailure() {

		IllegalFieldValueException exception = null;

		try {
			Challenge.create("cb")
					.availableFrom(48, 24, 3).to(2, 899, 2)
					.repeatEvery(3, DAY)
					.addLevel("level")
					.rewards(10)
					.withConditions()
					.valueOf("smth").greaterThan(30)
					.endChallenge();

		} catch (IllegalFieldValueException t) {
			exception = t;
		}

		assertNotNull(exception);
	}

	@Test
	public void negativePeriodFailure() {

		IllegalArgumentException exception = null;

		try {
			Challenge.create("cb")
					.availableFrom(23, 5, 2010).to(2, 2, 2002)
					.repeatEvery(2, WEEK)
					.addLevel("level")
					.rewards(20)
					.withConditions()
					.valueOf("smth").differentFrom(2)
					.endChallenge();
		} catch (IllegalArgumentException t) {
			exception = t;
		}

		assertNotNull(exception);
	}
}
