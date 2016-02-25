package fr.unice.polytech.ecoknowledge.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.calculator.producer.CalculatorProducer;
import fr.unice.polytech.ecoknowledge.calculator.worker.CalculatorWorker;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.time.*;
import fr.unice.polytech.ecoknowledge.feeder.producer.FeederProducer;
import fr.unice.polytech.ecoknowledge.feeder.worker.FeederWorker;
import org.joda.time.DateTime;
import org.junit.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * This test assumes that mongodb, activeMQ, and Ecoknowledge
 * front-end server are reachable and in a stable state
 */
public class TakeChallengeIntegrationTest {

	public static final String RESOURCE_NAME_CHALLENGE_EXAMPLE_JSON = "challenge-example.json";
	public static final String RESOURCE_NAME_USER_EXAMPLE_JSON = "user-example.json";

	public static final String NAME_OF_FEEDER_WORKER = "feederworker1";
	public static final int FEEDER_REFRESHING_FREQUENCY = 2500;

	public static final String NAME_OF_CALCULATOR_WORKER = "calculator1";
	public static final int CALCULATOR_REFRESHING_FREQUENCY = 2500;

	public static final String URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER = "http://localhost:8081/Ecoknowledge/";
	private static final String SERVICE_NAME_TO_POST_A_CHALLENGE = "challenges";
	private static final String SERVICE_NAME_TO_POST_A_USER = "users";
	public static final String TEST_DB_NAME = "test";
	private static final String SERVICE_NAME_TO_TAKE_A_CHALLENGE = "goals";

	private JsonObject fakePostChallengePayload;
	private JsonObject fakePostUserPayload;

	private List<Thread> listOfThread = new ArrayList<>();

	@Before
	public void setUp() throws IOException {
		MongoDBHandler.getInstance().getBddConnector().DB_NAME = TEST_DB_NAME;

		loadChallengeJsonDescription();
		loadUserJsonDescription();

		setUpCalculator();
		setUpFeeder();
	}


	@Test
	public void testWhenUserTakeGoal() throws InterruptedException {
		Thread.sleep(1500);
		String challengeID = postChallenge();
		assertNotNull(challengeID);

		Thread.sleep(500);

		//	-------------------------------------
		String userID = postAUser();
		assertNotNull(userID);

		String goalID = takeAChallenge(challengeID, userID);

		assertNotNull(goalID);

		System.out.printf("challengeID %s, UserID %s, GoalID %s\n", challengeID, userID, goalID);
		MongoDBHandler.getInstance().getBddConnector().drop(TEST_DB_NAME);
	}

	private String takeAChallenge(String challengeID, String userID) throws InterruptedException {
		JsonObject takeChallengePayload = new JsonObject();
		takeChallengePayload.addProperty("challenge", challengeID);
		takeChallengePayload.addProperty("user", userID);

		Response goalPostResponse = POST(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_TAKE_A_CHALLENGE, takeChallengePayload);
		Thread.sleep(500);

		assertEquals(200, goalPostResponse.getStatus());

		Object goalPostEntity = goalPostResponse.readEntity(String.class);
		return goalPostEntity.toString();
	}

	private String postAUser() throws InterruptedException {
		Response userPostResponse = POST(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_POST_A_USER, fakePostUserPayload);
		Thread.sleep(500);

		assertEquals(200, userPostResponse.getStatus());

		Object userPostEntity = userPostResponse.readEntity(String.class);
		return userPostEntity.toString();
	}

	private String postChallenge() throws InterruptedException {
		Response challengePostResponse = POST(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_POST_A_CHALLENGE, fakePostChallengePayload);
		Thread.sleep(500);

		assertEquals(200, challengePostResponse.getStatus());

		Object challengeEntity = challengePostResponse.readEntity(String.class);
		return challengeEntity.toString();
	}


	private JsonObject loadUserJsonDescription() throws IOException {
		fakePostUserPayload = loadResource(RESOURCE_NAME_USER_EXAMPLE_JSON);
		return fakePostUserPayload;
	}

	private JsonObject loadChallengeJsonDescription() throws IOException {
		fakePostChallengePayload = loadResource(RESOURCE_NAME_CHALLENGE_EXAMPLE_JSON);
		generateLifeSpan(fakePostChallengePayload);
		return fakePostChallengePayload;
	}

	private JsonObject loadResource(String resourceName) throws IOException {
		URL url = ClassLoader.getSystemClassLoader().getResource(resourceName);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

		if (bufferedReader == null) {
			System.err.println(url.getPath() + " resource not found !+");
			throw new FileNotFoundException(url.getPath());
		}

		JsonObject jsonObject = new JsonParser().parse(bufferedReader).getAsJsonObject();

		return jsonObject;
	}

	/**
	 * In order to make the test pass day and nigh, from monday to sunday,
	 * we generate a "fake" lifeSpan from the challenge description
	 * in order to create a goal
	 *
	 * @param jsonObject Full challenge json description
	 */
	private void generateLifeSpan(JsonObject jsonObject) {
		JsonObject generatedLifeSpan = new JsonObject();

		Clock.getClock().setFakeTime(new DateTime(2016,2,23,12,0,0));

		TimeBox generatedTimeBox = TimeSpanGenerator.generateTimeSpan(new Recurrence(RecurrenceType.WEEK, 1), Clock.getClock());
		DateTime startDate = generatedTimeBox.getStart();
		DateTime endDate = generatedTimeBox.getEnd();

		String startDateStr = startDate.toString();
		String endDateStr = endDate.toString();

		generatedLifeSpan.addProperty("start", startDateStr);
		generatedLifeSpan.addProperty("end", endDateStr);

		jsonObject.remove("lifeSpan");
		System.out.println("Fake lifeSpan : " + generatedLifeSpan);

		jsonObject.add("lifeSpan", generatedLifeSpan);
	}

	private void setUpFeeder() {
		FeederWorker feederWorker = new FeederWorker(NAME_OF_FEEDER_WORKER, 0);
		FeederProducer feederProducer = new FeederProducer(FEEDER_REFRESHING_FREQUENCY);

		thread(feederWorker, false);
		thread(feederProducer, false);
	}

	private void setUpCalculator() {
		CalculatorWorker calculatorWorker = new CalculatorWorker(NAME_OF_CALCULATOR_WORKER, 0);
		CalculatorProducer calculatorProducer = new CalculatorProducer(CALCULATOR_REFRESHING_FREQUENCY, -1);

		thread(calculatorWorker, false);
		thread(calculatorProducer, false);
	}

	private void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public static Response POST(String ipAddress, String service, JsonObject media) {
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ipAddress + service);
		Invocation.Builder b = resource.request();
		System.out.println("\t---> Sending request " + media.toString() + " to " + ipAddress + service + "'");

		Entity e = Entity.entity(media.toString(), MediaType.APPLICATION_JSON);

		return b.post(e);
	}
}
