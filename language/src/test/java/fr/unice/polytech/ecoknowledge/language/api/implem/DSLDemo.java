package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Benjamin on 01/03/2016.
 */
public class DSLDemo {

	@Test
	public void generateFrozenQueen() {
		Challenge c = Challenge.create("Reine des neiges")
				.withIcon("http://www.hucclecote.org.uk/wp-content/uploads/2015/12/Frozen-square.png")
				.availableFrom(14, 12, 2016).to(27, 12, 2016)
				.repeatEvery(1, DURATION_TYPE.WEEK)
				.addLevel("Sven")
					.withImage("http://media.melty.fr/article-2414899-ajust_930/sven-est-deja-tout-content-a-cette-idee.jpg")
					.rewards(200)
					.withConditions()
						.valueOf("TMP_AMB").lowerThan(25).atLeast(80).percentOfTime()
				.addLevel("Elsa")
					.withImage("http://www.glamour.com/images/entertainment/2014/06/elsa-frozen-square-w352.jpg")
					.rewards(500)
					.withConditions()
						.valueOf("TMP_AMB").lowerThan(20).atLeast(80).percentOfTime()
			.endChallenge();


		System.out.println(c.getDescription().toString(5));
	}

	// Image url
	// http://emea.lum.dolimg.com/v1/images/b5da8e4c0046a83b81dbd945719f6b354edd764b.jpeg

}