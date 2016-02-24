package fr.unice.polytech.ecoknowledge.integration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.calculator.producer.CalculatorProducer;
import fr.unice.polytech.ecoknowledge.calculator.worker.CalculatorWorker;
import fr.unice.polytech.ecoknowledge.domain.model.time.*;
import fr.unice.polytech.ecoknowledge.feeder.producer.FeederProducer;
import fr.unice.polytech.ecoknowledge.feeder.worker.FeederWorker;
import org.joda.time.DateTime;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This test assumes that mongodb, activeMQ, and Ecoknowledge
 * front-end server are reachable and in a stable state
 */
public class TakeChallengeIntegrationTest {

	public static final String NAME_OF_FEEDER_WORKER = "feederworker1";
	public static final int FEEDER_REFRESHING_FREQUENCY = 2500;
	public static final int CALCULATOR_REFRESHING_FREQUENCY = 2500;
	public static final String NAME_OF_CALCULATOR_WORKER = "calculator1";
	public static final String RESOURCE_NAME_CHALLENGE_EXAMPLE_JSON = "challenge-example.json";

	private JsonObject fakePostChallengePayload;

	@Before
	public void setUp() throws IOException {
		loadChallengeJsonDescription();
		setUpCalculator();
		setUpFeeder();
	}

	private JsonObject loadChallengeJsonDescription() throws IOException {
		URL url = ClassLoader.getSystemClassLoader().getResource(RESOURCE_NAME_CHALLENGE_EXAMPLE_JSON);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

		if (bufferedReader == null) {
			System.err.println(url.getPath() + " resource not found !+");
			throw new FileNotFoundException(url.getPath());
		}

		JsonObject jsonObject = new JsonParser().parse(bufferedReader).getAsJsonObject();

		generateLifeSpan(jsonObject);

		fakePostChallengePayload = jsonObject;

		return jsonObject;
	}

	/**
	 * In order to make the test pass day and nigh, from monday to sunday,
	 * we generate a "fake" lifeSpan from the challenge description
	 * in order to create a goal
	 * @param jsonObject
	 * 		Full challenge json description
	 */
	private void generateLifeSpan(JsonObject jsonObject) {
		JsonObject generatedLifeSpan = new JsonObject();

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

}
