package fr.unice.polytech.ecoknowledge.language.api.implem;

/**
 * Created by Sébastien on 25/11/2015.
 */

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;

public class COMP_ComplexChallengeTest {

	@Test
	public void complexChallenge() {

		Challenge.create("Arbitrary complex challenge")
				.availableFrom(2, 11, 2015).to(1, 4, 2016)
				.repeatEvery(9, DAY)
				.addLevel("First level")
				.rewards(30)
				.withConditions()
				.averageOf("SOMETHING").greaterThan(30).on(WEEK_PERIOD.WEEK_DAYS, DAY_MOMENT.AFTERNOON)
				.and()
				.valueOf("SOMETHING_ELSE").lowerThan(2)
				.and()
				.valueOf("SOMETHING_ELSE_AGAIN").lowerThan(30).on(WEEK_PERIOD.WEEK_DAYS).atLeast(3).percentOfTime()
				.endChallenge();

	}

	@Test
	public void atLeastAfterCompare() {

		Challenge.create("lowerThan().atLeast()")
				.availableFrom(3, 5, 2014).to(4, 5, 2014)
				.repeatEvery(1, DAY)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("SENSOR").lowerThan(5).atLeast(5).times()
				.endChallenge();
	}

}
