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
import org.glassfish.jersey.client.ClientResponse;
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
	private static final String SERVICE_NAME_TO_TAKE_A_CHALLENGE = "goals";
	public static final int WAITING_TIME_BETWEEN_REQUESTS = 1500;
	public static final int INITIAL_WAITING_TIME = 1500;
	private static final String SERVICE_NAME_TO_GET_GOALS_RESULT = "goals";
	public static final int WAITING_TIME_AFTER_GET = 500;
	public static final int WAITING_TIME_AFTER_POST = 500;

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
		//setUpFeeder();

	}

	@After
	public void tearDown() throws GoalNotFoundException, ChallengeNotFoundException, UserNotFoundException {
		MongoDBHandler.getInstance().getBddConnector().deleteGoalByID(goalID);
		MongoDBHandler.getInstance().getBddConnector().deleteChallengeByID(challengeID);
		MongoDBHandler.getInstance().getBddConnector().deleteUserByID(userID);
		MongoDBHandler.getInstance().getBddConnector().deleteGoalResultByID(goalResultID);
	}


	@Test
	public void testWhenUserTakeGoal() throws InterruptedException {
		Thread.sleep(INITIAL_WAITING_TIME);

		System.out.println("Posting a challenge ...");
		challengeID = postChallenge();
		assertNotNull(challengeID);
		Thread.sleep(WAITING_TIME_BETWEEN_REQUESTS);

		System.out.println("Posting a  user ...");
		userID = postAUser();
		assertNotNull(userID);
		Thread.sleep(WAITING_TIME_BETWEEN_REQUESTS);

		System.out.println("Taking a challenge  ...");
		goalID = takeAChallenge(challengeID, userID);
		assertNotNull(goalID);
		Thread.sleep(WAITING_TIME_BETWEEN_REQUESTS);

		System.out.printf("challengeID %s, UserID %s, GoalID %s\n", challengeID, userID, goalID);

		Thread.sleep(CALCULATOR_REFRESHING_FREQUENCY*2);

		System.out.println("Getting goal result  ...");
		JsonArray allGoalResult = getGoalResult();
		assertNotNull(allGoalResult);

		int expectedNumberOfGoalsResult = 1;
		int actualNumberOfGoalsResult = allGoalResult.size();
		assertEquals(expectedNumberOfGoalsResult, actualNumberOfGoalsResult);

		JsonObject goalResult = allGoalResult.get(0).getAsJsonObject();
		assertNotNull(goalResult);

		String expectedGoalResultID = goalID;
		String actualGoalResultID = goalResult.get("id").getAsString();
		assertEquals(expectedGoalResultID, actualGoalResultID);

		goalResultID = goalID;

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


		//	Wait for computation

		//	Retrieve result

		//	See if it match

		//	Make goal success (with other fake data)

		//	Set fake time to after goal timespan.end

		//	Get badges of user

		//	Check that badge has been earned

		//Thread.sleep(CALCULATOR_REFRESHING_FREQUENCY*20);

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

		Clock.getClock().setFakeTime(new DateTime(2016,2,23,12,0,0));


		//	Set time of distant domain server
		JsonObject newTimeInJson = new JsonObject();
		newTimeInJson.addProperty("newTime", Clock.getClock().getTime().getMillis());
		postRequest(URL_OF_ECOKNOWLEDGE_FRONTEND_SERVER, "test/clock", newTimeInJson);


		TimeBox generatedTimeBox = TimeSpanGenerator.generateTimeSpan(new Recurrence(RecurrenceType.WEEK, 1), Clock.getClock());
		DateTime startDate = generatedTimeBox.getStart();
		DateTime endDate = generatedTimeBox.getEnd();

		String startDateStr = startDate.toString();
		String endDateStr = endDate.toString();

		generatedLifeSpan.addProperty("start", startDateStr);
		generatedLifeSpan.addProperty("end", endDateStr);

		jsonObject.remove("lifeSpan");

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

	public static Response GET(String ipAddress, String service, Map<String, Object> urlParameters) {

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(ipAddress + service).queryParam("userID",urlParameters.get("userID").toString());

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
