package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class SEND_SendTest {

	@Test
	public void sendForReal() {

		Challenge c = Challenge.create("I don't want it hot")
				.withIcon("http://divagirl-inc.com/fitness/files/2011/11/pretty-xmas-girl.jpg")
				.availableFrom(1,12).to(8,4,2016)
				.noRepeat()
				.addLevel("Pretty good")
					.rewards(50)
					.withConditions()
					.valueOf("TMP").lowerThan(32)
				.endChallenge();
		//c.send();

		Challenge challenge = Challenge.create("Make it frozen")
				.withIcon("https://s-media-cache-ak0.pinimg.com/736x/84/b5/85/84b585907446906a7b55d1b698a83160.jpg")
				.availableFrom(7,12,2015).to(22,12,2015)
				.repeatEvery(1, WEEK)
				.addLevel("Pretty cold")
				.withImage("http://snowbrains.com/wp-content/uploads/2013/05/w031230b033.jpg")
				.rewards(10)
				.withConditions()
				.valueOf("TMP").lowerThan(25).atLeast(80).percentOfTime()
				.addLevel("Really the north")
				.withImage("http://svowebmaster.free.fr/images_site_svo/armes/armes_picardie.gif")
				.rewards(20)
				.withConditions()
				.valueOf("TMP").lowerThan(22).atLeast(90).percentOfTime()
				.endChallenge();

	}
}
