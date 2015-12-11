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

		Challenge.create("Make it frozen")
				//.dontSend()
				.withIcon("https://s-media-cache-ak0.pinimg.com/736x/84/b5/85/84b585907446906a7b55d1b698a83160.jpg")
				.availableFrom(7,12,2015).to(22,12,2015)
				.during(1, WEEK)
				.atLevel("Pretty cold")
				.withImage("http://snowbrains.com/wp-content/uploads/2013/05/w031230b033.jpg")
				.rewards(10)
				.withConditions()
				.valueOf("TMP").lowerThan(25).atLeast(80).percent()
				.atLevel("Really the north")
				.withImage("http://svowebmaster.free.fr/images_site_svo/armes/armes_picardie.gif")
				.rewards(20)
				.withConditions()
				.valueOf("TMP").lowerThan(22).atLeast(90).percent()
				.end();
	}
}
