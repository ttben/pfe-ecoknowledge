package fr.unice.polytech.ecoknowledge.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.calculator.producer.CalculatorProducer;
import fr.unice.polytech.ecoknowledge.calculator.worker.CalculatorWorker;
import fr.unice.polytech.ecoknowledge.data.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

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
	public static final String URL_OF_FAKE_DATA_SERVICE = "http://localhost:8081/fakeDataSource/";

	private static final String SERVICE_NAME_TO_POST_A_CHALLENGE = "challenges";
	private static final String SERVICE_NAME_TO_POST_A_USER = "users";
	private static final String SERVICE_NAME_TO_TAKE_A_CHALLENGE = "goals";
	public static final int WAITING_TIME_BETWEEN_REQUESTS = 5500;
	public static final int INITIAL_WAITING_TIME = 1500;
	private static final String SERVICE_NAME_TO_GET_GOALS_RESULT = "goals";
	public static final int WAITING_TIME_AFTER_GET = 1500;
	public static final int WAITING_TIME_AFTER_POST = 1500;
	public static final DateTime NOW_FAKE_TIME = new DateTime(2016, 2, 23, 12, 0, 0);
	private static final String SERVICE_NAME_TO_GET_BADGES = "badges";
	private static final String SERVICE_NAME_TO_GET_GOALS = "goals";

	private JsonObject fakePostChallengePayload;
	private JsonObject fakePostUserPayload;

	private String challengeID;
	private String userID;
	private String goalID;

	private List<Thread> listOfThread = new ArrayList<>();
	private String goalResultID;

	@Before
	public void setUp() throws IOException, InterruptedException {
		loadChallengeJsonDescription();
		loadUserJsonDescription();

		setUpCalculator();
		Thread.sleep(250);

		setUpFeeder();
		Thread.sleep(250);
	}

	@After
	public void tearDown() throws GoalNotFoundException, ChallengeNotFoundException, UserNotFoundException {
		/*
		MongoDBHandler.getInstance().getBddConnector().deleteGoalByID(goalID);
		MongoDBHandler.getInstance().getBddConnector().deleteChallengeByID(challengeID);
		MongoDBHandler.getInstance().getBddConnector().deleteUserByID(userID);
		MongoDBHandler.getInstance().getBddConnector().deleteGoalResultByID(goalResultID);
		*/
	}


	@Test
	public void testWhenUserTakeGoal() throws InterruptedException {
		Thread.sleep(INITIAL_WAITING_TIME);

		//	Posting a challenge
		System.out.println("Posting a challenge ...");
		challengeID = postChallenge();
		assertNotNull(challengeID);
		Thread.sleep(WAITING_TIME_BETWEEN_REQUESTS);


		//	Posting a user
		System.out.println("Posting a  user ...");
		userID = postAUser();
		assertNotNull(userID);
		Thread.sleep(WAITING_TIME_BETWEEN_REQUESTS);


		//	Posting a challenge
		System.out.println("Taking a challenge  ...");
		goalID = takeAChallenge(challengeID, userID);
		assertNotNull(goalID);
		Thread.sleep(WAITING_TIME_BETWEEN_REQUESTS);

		System.out.printf("challengeID %s, UserID %s, GoalID %s\n", challengeID, userID, goalID);

		Thread.sleep(CALCULATOR_REFRESHING_FREQUENCY*2);


		//	Retrieving calculator output
		System.out.println("Getting goal result  ...");
		JsonArray allGoalResult = getGoalResult();
		assertNotNull(allGoalResult);

		//	Check if there is exactly one result
		int expectedNumberOfGoalsResult = 1;
		int actualNumberOfGoalsResult = allGoalResult.size();
		assertEquals(expectedNumberOfGoalsResult, actualNumberOfGoalsResult);

		JsonObject goalResult = allGoalResult.get(0).getAsJsonObject();
		assertNotNull(goalResult);


		//	Check that this goal result has same id than goal
		String expectedGoalResultID = goalID;
		String actualGoalResultID = goalResult.get("id").getAsString();
		assertEquals(expectedGoalResultID, actualGoalResultID);

		goalResultID = goalID;


		//	Check goal result's content
		int expectedNumberOfLevels = fakePostChallengePayload.get("levels").getAsJsonArray().size();
		int actualNumberOfLevels = goalResult.get("levels").getAsJsonArray().size();
		assertEquals(expectedNumberOfLevels, actualNumberOfLevels);

		String expectedGoalName = fakePostChallengePayload.get("name").getAsString();
		String actualGoalName = goalResult.get("name").getAsString();
		assertEquals(expectedGoalName, actualGoalName);

		int expectedTimePercent = 20;
		int actualTimePercent = goalResult.get("timePercent").getAsInt();
		assertEquals(expectedTimePercent, actualTimePercent);

		//	Send fake data
		sendFakeData();

		//	Wait for computation
		int waitingTime = (CALCULATOR_REFRESHING_FREQUENCY + FEEDER_REFRESHING_FREQUENCY)*2;
		System.out.println("Waiting for computation for " + waitingTime + " millis");
		Thread.sleep(waitingTime);

		//	Retrieve result
		allGoalResult = getGoalResult();
		Thread.sleep(WAITING_TIME_AFTER_GET);

		//	See if it match
		assertEquals(1, allGoalResult.size());
		goalResult = allGoalResult.get(0).getAsJsonObject();

		System.out.println("GOAL RESULT : " + goalResult);

		double expectedProgressPercent = 75.0;
		double actualProgressPercent = goalResult.get("progressPercent").getAsDouble();
		assertEquals(expectedProgressPercent, actualProgressPercent);


		//	Make goal success (with other fake data)
		sendFakeData("TEMP_555",NOW_FAKE_TIME.minusHours(2), 42);	//	Must be < 44 at least 2 times


		//	Wait for computation
		waitingTime = (CALCULATOR_REFRESHING_FREQUENCY + FEEDER_REFRESHING_FREQUENCY)*2;
		System.out.println("Waiting for computation for " + waitingTime + " millis");
		Thread.sleep(waitingTime);

		//	Retrieve new result
		allGoalResult = getGoalResult();
		Thread.sleep(WAITING_TIME_AFTER_GET);

		//	See if it match
		assertEquals(1, allGoalResult.size());
		goalResult = allGoalResult.get(0).getAsJsonObject();

		expectedProgressPercent = 100.0;
		actualProgressPercent = goalResult.get("progressPercent").getAsDouble();
		assertEquals(expectedProgressPercent, actualProgressPercent);

		//	Set fake time to after goal timespan.end
		DateTime endOfTheWeek = new DateTime(2016,2,27,12,0,0);
		setFakeTime(endOfTheWeek);

		//	Get badges of user
		JsonArray badges = getBadgesOfUser();
		assertNotNull(badges);

		//	Check that badge has been earned
		int expectedNumberOfBadges = 1;
		int actualNumberOfBadges = badges.size();
		assertEquals(expectedNumberOfBadges, actualNumberOfBadges);

		JsonObject badgeEarned = badges.get(0).getAsJsonObject();
		assertNotNull(badgeEarned);

		// FIXME: 28/02/2016 not correct du to a TODO in badgeView
		String expectedChallengeName = "Olaf powa";
		String actualChallengeName = badgeEarned.get("nameChallenge").getAsString();
		assertEquals(expectedChallengeName, actualChallengeName);

		String expectedLevelName = "Olaf powa";
		String actualLevelName = badgeEarned.get("nameLevel").getAsString();
		assertEquals(expectedLevelName, actualLevelName);

		int expectedPoints = 200;
		int actualPointsEarned = badgeEarned.get("points").getAsInt();
		assertEquals(expectedPoints, actualPointsEarned);

		int expectedNumberPossessedExpected = 1;
		int actualNumberPossessed = badgeEarned.get("numberPossessed").getAsInt();
		assertEquals(expectedNumberPossessedExpected, actualNumberPossessed);

		//	Check that another goal has been created
		JsonArray goals = getGoalsOfUser();
		assertNotNull(goals);

		int expectedNumberOfGoals = 1;
		int actualNumberOfGoals = goals.size();
		assertEquals(expectedNumberOfGoals, actualNumberOfGoals);

		JsonObject newGoal = goals.get(0).getAsJsonObject();
		assertNotNull(newGoal);

		System.out.println("NEW GOAL: " + newGoal);

		String expectedChallengeID = challengeID;
		String challengeOfNewGoal = newGoal.get("challenge").getAsString();
		assertEquals(expectedChallengeID, challengeOfNewGoal);

		String expectedUserID = userID;
		String userOfNewGoal = newGoal.get("user").getAsString();
		assertEquals(expectedUserID, userOfNewGoal);

		String idOfNewGoal = newGoal.get("id").getAsString();
		assertFalse(goalID.equals(idOfNewGoal));

		Thread.sleep(2500);
	}

	private JsonArray getBadgesOfUser() throws InterruptedException {
		Map<String, Object> urlParameters = new HashMap<>();
		urlParameters.put("userID", userID);

		String resultGet = getRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_GET_BADGES, urlParameters);

		return new JsonParser().parse(resultGet).getAsJsonArray();
	}

	private JsonArray getGoalsOfUser() throws InterruptedException {
		String resultGet = getRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_GET_GOALS, null);

		return new JsonParser().parse(resultGet).getAsJsonArray();
	}

	private String getRequest(String urlOfFrontendServer, String serviceName, Map<String, Object> urlParameters) throws InterruptedException {
		Response response = GET(urlOfFrontendServer, serviceName,urlParameters);
		Thread.sleep(WAITING_TIME_AFTER_GET);

		assertEquals(200, response.getStatus());

		String entity = response.readEntity(String.class).toString();

		return entity;
	}

	private String sendFakeData() throws InterruptedException {
		sendFakeData("TEMP_443V",NOW_FAKE_TIME.minusDays(1), 30);	//	Must be > 27 at least 50% of times
		sendFakeData("TEMP_555",NOW_FAKE_TIME.minusDays(1), 30);	//	Must be < 44 at least 2 times
		return "sendFakeData";
	}

	private String sendFakeData(String sensorName, DateTime date, double value) throws InterruptedException {
		String aSensorName = sensorName;	//	Must be > 27 at least 50% of times

		JsonObject firstPayloadValue = new JsonObject();
		firstPayloadValue.addProperty("date", date.getMillis());
		firstPayloadValue.addProperty("value", value);

		JsonObject payload = new JsonObject();
		payload.addProperty("targetSensor", aSensorName);
		payload.add("value", firstPayloadValue);

		return postRequest(URL_OF_FAKE_DATA_SERVICE, "fakeData", payload);
	}

	private JsonArray getGoalResult() throws InterruptedException {
		Map<String, Object> urlParameters = new HashMap<>();
		urlParameters.put("userID", userID);
		
		Response response = GET(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_GET_GOALS_RESULT,urlParameters);
		Thread.sleep(WAITING_TIME_AFTER_GET);

		assertEquals(200, response.getStatus());

		String entity = response.readEntity(String.class).toString();

		return new JsonParser().parse(entity).getAsJsonArray();
	}

	private String takeAChallenge(String challengeID, String userID) throws InterruptedException {
		JsonObject takeChallengePayload = new JsonObject();
		takeChallengePayload.addProperty("challenge", challengeID);
		takeChallengePayload.addProperty("user", userID);

		JsonObject goal= new JsonParser().parse(postRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_TAKE_A_CHALLENGE, takeChallengePayload)).getAsJsonObject();

		return goal.get("id").getAsString();
	}

	private String postAUser() throws InterruptedException {

		String userIdResponse = postRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_POST_A_USER, fakePostUserPayload);

		System.out.println("USER ID RESPONSE : " + userIdResponse);

		JsonObject responsePayload = new JsonParser().parse(userIdResponse).getAsJsonObject();
		return responsePayload.get("id").getAsString();
	}

	private String postChallenge() throws InterruptedException {
		return postRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, SERVICE_NAME_TO_POST_A_CHALLENGE, fakePostChallengePayload);
	}

	private String postRequest(String url, String service, JsonObject payload) throws InterruptedException {
		Response statusPostResponse = POST(url, service, payload);
		Thread.sleep(WAITING_TIME_AFTER_POST);

		Object entity = statusPostResponse.readEntity(String.class);
		System.out.println("Receiving response : " + statusPostResponse + " containing : " + entity);

		assertEquals(200, statusPostResponse.getStatus());

		return entity.toString();
	}


	private JsonObject loadUserJsonDescription() throws IOException {
		fakePostUserPayload = loadResource(RESOURCE_NAME_USER_EXAMPLE_JSON);
		return fakePostUserPayload;
	}

	private JsonObject loadChallengeJsonDescription() throws IOException, InterruptedException {
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
	private void generateLifeSpan(JsonObject jsonObject) throws InterruptedException {
		JsonObject generatedLifeSpan = new JsonObject();

		setFakeTime(NOW_FAKE_TIME);

		TimeBox generatedTimeBox = TimeSpanGenerator.generateTimeSpan(new Recurrence(RecurrenceType.WEEK, 1), Clock.getClock());
		DateTime startDate = generatedTimeBox.getStart();
		DateTime endDate = generatedTimeBox.getEnd().plusWeeks(1);

		String startDateStr = startDate.toString();
		String endDateStr = endDate.toString();

		generatedLifeSpan.addProperty("start", startDateStr);
		generatedLifeSpan.addProperty("end", endDateStr);

		jsonObject.remove("lifeSpan");

		jsonObject.add("lifeSpan", generatedLifeSpan);
	}

	private void setFakeTime(DateTime newNow) throws InterruptedException {
		Clock.getClock().setFakeTime(newNow);

		//	Set time of distant domain server
		JsonObject newTimeInJson = new JsonObject();
		newTimeInJson.addProperty("newTime", Clock.getClock().getTime().getMillis());
		postRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, "test/clock", newTimeInJson);
		Thread.sleep(WAITING_TIME_AFTER_POST);
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

	public static Response GET(String ipAddress, String service, Map<String, Object> urlParameters) {

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(ipAddress + service);
		if(urlParameters != null) {
			resource = resource.queryParam("userID", urlParameters.get("userID").toString());
		}

		Invocation.Builder b = resource.request();

		System.out.println("\t---> Sending request to " + ipAddress +  service + " URI : " + resource.toString());

		return b.get();
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
