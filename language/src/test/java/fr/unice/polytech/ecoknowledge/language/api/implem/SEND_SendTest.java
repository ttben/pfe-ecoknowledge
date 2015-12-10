package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import org.junit.Ignore;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.MONTH;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class SEND_SendTest {

	@Test
	public void sendForReal() {

		ChallengeBuilder cb = Challenge.create("I want it hotter");
		cb
				.dontSend()
				.withIcon("http://divagirl-inc.com/fitness/files/2011/11/pretty-xmas-girl.jpg")
				.availableFrom(1,12).to(20,12)
				.during(1, WEEK)
				.atLevel("Pretty good")
					.rewards(50)
					.withConditions()
					.increase("TMP").by(5).percent().comparedTo(OLD_PERIOD.LAST_WEEK)
				.end()
		;

		ChallengeBuilder cb2 = Challenge.create("La reine des neiges FROM DSL");
			cb2
                    .dontSend()
				.withIcon("http://static.cotemaison.fr/medias_8904/w_1024,h_445,c_crop,x_0,y_167/w_1520,h_855,c_fill,g_north/v1393340192/climatisation-nos-astuces-pour-une-maison-fraiche_4559310.jpg")
				.availableFrom(30, 11, 2015).to(20, 12, 2015)
				.during(1, DURATION_TYPE.WEEK)
					.atLevel("Sven")
						.withImage("http://media.melty.fr/article-2414899-ajust_930/sven-est-deja-tout-content-a-cette-idee.jpg")
						.rewards(100)
						.withConditions()
							.valueOf("TMP").greaterThan(27)
							.atLeast(50).percent()
						.and()
							.valueOf("TMP").lowerThan(44)
							.atLeast(2).times()
					.atLevel("OLAF")
						.withImage("http://www.tvhland.com/vignette/image/201312/52becf9e1fd16.jpg")
						.rewards(200)
						.withConditions()
							.valueOf("TMP").greaterThan(27)
							.atLeast(60).percent()
						.and()
							.valueOf("TMP").lowerThan(44)
							.atLeast(2).times()
				.end();

	}
}
