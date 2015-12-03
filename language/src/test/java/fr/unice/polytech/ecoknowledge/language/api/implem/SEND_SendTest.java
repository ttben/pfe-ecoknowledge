package fr.unice.polytech.ecoknowledge.language.api.implem;

import org.junit.Ignore;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class SEND_SendTest {

	@Ignore
	@Test
	public void sendForReal() {

		ChallengeBuilder cb = Challenge.create("For real");
		cb
				.availableFrom(23, 11, 2015).to(7, 3, 2016)
				.during(1, WEEK)
				.atLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("We did it").greaterThan(9000)
				.end();
	}
}
