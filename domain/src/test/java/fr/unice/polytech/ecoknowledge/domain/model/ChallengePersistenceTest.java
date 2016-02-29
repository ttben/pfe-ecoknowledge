package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.data.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ChallengePersistenceTest {

	public static final String FILENAME = "challenge-example-sample1.json";
	static Challenge aChallenge = null;
	static String oldDBName;
	static String testDBName = "challengePersistenceTest";
	JsonObject jsonObject = null;

	@BeforeClass
	@Ignore
	public static void changeDB() {
		oldDBName = MongoDBConnector.DB_NAME;
		MongoDBConnector.DB_NAME = testDBName;
	}

	@AfterClass
	@Ignore
	public static void resetDB() {
		MongoDBConnector.DB_NAME = oldDBName;
		Controller.getInstance().drop(testDBName);
	}

	@AfterClass
	public static void tearDown() {
		// TODO: 06/12/2015 clean bdd
	}

	@Before
	@Ignore
	public void loadJsonFile() throws Exception {

		jsonObject = TestUtils.getFakeJson(FILENAME);
	}

	@Test
	@Ignore
	public void aChallenge_WhenCreatedAndPersisted_CanBeCreatedAndIsTheSame() throws IOException, NotSavableElementException, ChallengeNotFoundException, NotReadableElementException {
		ObjectMapper objectMapper = new ObjectMapper();
		Challenge challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		MongoDBHandler.getInstance().store(challenge);
		aChallenge = MongoDBHandler.getInstance().readChallengeByID(challenge.getId().toString());

		assertEquals(challenge, aChallenge);
	}
}
