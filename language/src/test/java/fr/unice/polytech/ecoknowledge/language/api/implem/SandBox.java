package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD;
import org.json.JSONObject;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Benjamin on 01/03/2016.
 */
public class SandBox {
	public static final String URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER = "http://localhost:8080/Ecoknowledge/";
	private static final String SERVICE_NAME_TO_POST_A_CHALLENGE = "challenges";
	public static final int WAITING_TIME_AFTER_POST = 1500;


	public static void main(String[] args) throws InterruptedException {
		JSONObject generateFrozenQueen = generateFrozenQueen();
		JSONObject generateHelios = generateHelios();
		JSONObject generateNoisy = generateNoisy();
		JSONObject generateQuiet = generateQuiet();
		JSONObject generateHeat = generateHeat();

		postChallenge(generateFrozenQueen);
		postChallenge(generateHelios);
		postChallenge(generateNoisy);
		postChallenge(generateQuiet);
		postChallenge(generateHeat);
	}
	
	private static JSONObject generateHeat() {
		Challenge c = Challenge.create("Magmar")
				.withIcon("http://guidesmedia.ign.com/guides/059687/images/blackwhite/pokemans_126.gif")
				.availableFrom(1, 1, 2016).to(30, 3, 2016)
				.repeatEvery(1, DURATION_TYPE.WEEK)

				.addLevel("Magby")
					.withImage("http://www.pokepedia.fr/images/thumb/3/33/Magby-HGSS.png/250px-Magby-HGSS.png")
					.rewards(100)
					.withConditions()
						.increase("TMP_CLIM").by(10).percent().comparedTo(OLD_PERIOD.LAST_MONTH)
					.and()
						.increase("TMP_AMB").by(10).percent().comparedTo(OLD_PERIOD.LAST_MONTH)

				.addLevel("Magmar")
					.withImage("http://guidesmedia.ign.com/guides/059687/images/blackwhite/pokemans_126.gif")
					.rewards(100)
					.withConditions()
						.increase("TMP_CLIM").by(20).percent().comparedTo(OLD_PERIOD.LAST_MONTH)
					.and()
						.increase("TMP_AMB").by(20).percent().comparedTo(OLD_PERIOD.LAST_MONTH)

				.addLevel("Magmortar")
					.withImage("http://cdn.bulbagarden.net/upload/thumb/6/60/467Magmortar.png/250px-467Magmortar.png")
					.rewards(100)
					.withConditions()
						.increase("TMP_CLIM").by(25).percent().comparedTo(OLD_PERIOD.LAST_MONTH)
					.and()
						.increase("TMP_AMB").by(25).percent().comparedTo(OLD_PERIOD.LAST_MONTH)

				.endChallenge();

		return c.getDescription();
	}

	private static JSONObject generateQuiet() {
		Challenge c = Challenge.create("Chut on travaille")
				.withIcon("http://cdn2.kevinmd.com/blog/wp-content/uploads/shutterstock_143614549.jpg")
				.availableFrom(1, 1, 2016).to(30, 3, 2016)
				.repeatEvery(1, DURATION_TYPE.WEEK)
				.addLevel("Bibliothèque")
					.withImage("http://www.scenolia.com/2026/bibliotheque.jpg")
					.rewards(100)
					.withConditions()
						.valueOf("SOUND").lowerThan(40).atLeast(80).percentOfTime()
				.addLevel("Monastère")
					.withImage("http://fr.topic-topos.com/image-bd/france/56/cloitre-du-monastere-des-carmes-ploermel.jpg")
					.rewards(100)
					.withConditions()
						.valueOf("SOUND").lowerThan(30).atLeast(80).percentOfTime()
				.addLevel("Learnig centre SophiaTech")
					.withImage("http://bibliotheque-blogs.unice.fr/neurones/wp-content/uploads/sites/6/2015/03/leadImage.jpg")
					.rewards(100)
					.withConditions()
						.valueOf("SOUND").lowerThan(20).atLeast(80).percentOfTime()
				.endChallenge();

		return c.getDescription();
	}

	private static JSONObject generateNoisy() {
		Challenge c = Challenge.create("Bruyant")
				.withIcon("http://www.interson-protac.com/js/tinymce/jscripts/tiny_mce/plugins/imagemanager/files/bruit1.jpg")
				.availableFrom(1, 1, 2016).to(30, 3, 2016)
				.repeatEvery(1, DURATION_TYPE.WEEK)
				.addLevel("Si3")
					.withImage("http://img.actionco.fr/Img/BREVE/2013/10/229991/Infographie-comportements-travail-dans-monde-F.jpg")
					.rewards(100)
					.withConditions()
						.valueOf("SOUND").greaterThan(20).atLeast(50).percentOfTime()
				.addLevel("Tronçonneuse")
					.withImage("http://www.adepem.com/img/logos/logo-tronconneuse.png")
					.rewards(100)
					.withConditions()
						.valueOf("SOUND").greaterThan(60).atLeast(50).percentOfTime()
				.addLevel("Concert")
					.withImage("https://dachaontherock.files.wordpress.com/2011/01/foule-concert.jpg")
					.rewards(100)
					.withConditions()
						.valueOf("SOUND").greaterThan(65).atLeast(60).percentOfTime()
				.endChallenge();

		return c.getDescription();
	}

	private static JSONObject generateHelios() {
		Challenge c = Challenge.create("Aérateur")
				.withIcon("https://pbs.twimg.com/profile_images/1400182672/pitr_Window_icon.png")
				.availableFrom(1, 1, 2016).to(30, 3, 2016)
				.repeatEvery(1, DURATION_TYPE.WEEK)
				.addLevel("Petit ventilateur")
					.withImage("http://cliparts.co/cliparts/pco/ABX/pcoABXgzi.png")
					.rewards(100)
					.withConditions()
						.valueOf("DOOR_OPEN").equalsTo(0).atLeast(50).percentOfTime()
					.and()
						.valueOf("WINDOW_OPEN").equalsTo(0).atLeast(50).percentOfTime()
				.addLevel("Tornado")
					.withImage("http://www.crh.noaa.gov/Image/dmx/IowaTors/tornado-icon-EF2.png")
					.rewards(150)
					.withConditions()
						.valueOf("DOOR_OPEN").equalsTo(0).atLeast(70).percentOfTime()
					.and()
						.valueOf("WINDOW_OPEN").equalsTo(0).atLeast(70).percentOfTime()
				.addLevel("Helios")
					.withImage("http://orig05.deviantart.net/558c/f/2012/002/b/5/helios___greek_god_of_the_sun_by_urbandesing-d4l0voa.jpg")
					.rewards(200)
					.withConditions()
						.valueOf("DOOR_OPEN").equalsTo(0).atLeast(85).percentOfTime()
					.and()
						.valueOf("WINDOW_OPEN").equalsTo(0).atLeast(85).percentOfTime()
		.endChallenge();

		return c.getDescription();
	}

	private static JSONObject generateFrozenQueen() {
		Challenge c = Challenge.create("Reine des neiges")
				.withIcon("http://www.glamour.com/images/entertainment/2014/06/elsa-frozen-square-w352.jpg")
				.availableFrom(1, 1, 2016).to(30, 3, 2016)
				.repeatEvery(1, DURATION_TYPE.WEEK)
				.addLevel("Sven")
					.withImage("http://media.melty.fr/article-2414899-ajust_930/sven-est-deja-tout-content-a-cette-idee.jpg")
					.rewards(100)
					.withConditions()
						.valueOf("TMP_CLIM").lowerThan(27).atLeast(50).percentOfTime()
					.and()
						.valueOf("TMP_AMB").lowerThan(25).atLeast(2).times()
				.addLevel("Olaf")
					.withImage("http://www.tvhland.com/vignette/image/201312/52becf9e1fd16.jpg")
					.rewards(150)
					.withConditions()
						.valueOf("TMP_CLIM").lowerThan(20).atLeast(70).percentOfTime()
					.and()
						.valueOf("TMP_AMB").lowerThan(18).atLeast(2).times()
				.addLevel("Anna")
					.withImage("http://emea.lum.dolimg.com/v1/images/b5da8e4c0046a83b81dbd945719f6b354edd764b.jpeg")
					.rewards(200)
					.withConditions()
						.valueOf("TMP_CLIM").lowerThan(18).atLeast(75).percentOfTime()
					.and()
						.valueOf("TMP_AMB").lowerThan(17).atLeast(4).times()
			.endChallenge();

		return c.getDescription();
	}

	private static String postChallenge(JSONObject challengeJsonDescription) throws InterruptedException {
		return postRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_POST_A_CHALLENGE, challengeJsonDescription);
	}

	private static String postRequest(String url, String service, JSONObject payload) throws InterruptedException {
		Response statusPostResponse = POST(url, service, payload);
		Thread.sleep(WAITING_TIME_AFTER_POST);

		Object entity = statusPostResponse.readEntity(String.class);
		System.out.println("Receiving response : " + statusPostResponse + " containing : " + entity);

		return entity.toString();
	}

	public static Response POST(String ipAddress, String service, JSONObject media) {
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ipAddress + service);
		Invocation.Builder b = resource.request();
		System.out.println("\t---> Sending request " + media.toString() + " to " + ipAddress + service + "'");

		Entity e = Entity.entity(media.toString(), MediaType.APPLICATION_JSON);

		return b.post(e);
	}

}