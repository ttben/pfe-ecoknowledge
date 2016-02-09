package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import core.MongoDBConnector;
import exceptions.ChallengeNotFoundException;
import exceptions.NotReadableElementException;
import exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ChallengePersistenceTest {

	JsonObject jsonObject = null;

	static Challenge aChallenge = null;
	static String oldDBName;
	static String testDBName = "challengePersistenceTest";

	@BeforeClass
	@Ignore
	public static void changeDB(){
		oldDBName = MongoDBConnector.DB_NAME;
		MongoDBConnector.DB_NAME = testDBName;
	}

	@AfterClass
	@Ignore
	public static void resetDB(){
		MongoDBConnector.DB_NAME = oldDBName;
		Controller.getInstance().drop(testDBName);
	}

	@Before
	@Ignore
	public void loadJsonFile() {

		jsonObject = TestUtils.getFakeJson(1);
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

	@AfterClass
	public static void tearDown() {
		// TODO: 06/12/2015 clean bdd
	}
}
