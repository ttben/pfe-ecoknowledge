package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.Model;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import fr.unice.polytech.ecoknowledge.domain.data.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.utils.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

// FIXME: 04/12/2015 THIS TEST DUMP A GOAL IN THE BDD WITHOUT ERASING IT
public class GoalEvaluationTest {

	private static JsonObject challengeJsonDescription = null;
	private static JsonObject userJsonDescription = null;
	private static JsonObject goalJsonDescription = null;

	private static String challengeId = "challengeId";
	private static String userId = "userId";
	private static String goalId = "goalId";
	private static Challenge aChallenge = null;
	private static User aUser = null;
	private static Goal aGoal = null;


	@BeforeClass
	public static void loadJsonFile() throws IOException, IncoherentDBContentException, NotReadableElementException, GoalNotFoundException, NotSavableElementException {
		MongoDBConnector.DB_NAME = "test";

		ObjectMapper objectMapper = new ObjectMapper();

		challengeJsonDescription = TestUtils.getFakeJson(3);

		System.out.println("\n\n\n" + challengeJsonDescription.toString());
		aChallenge = (Challenge) objectMapper.readValue(challengeJsonDescription.toString(), Challenge.class);
		challengeId = aChallenge.getId().toString();
		MongoDBHandler.getInstance().store(aChallenge);

		userJsonDescription = TestUtils.getFakeUser(1);
		aUser = (User) objectMapper.readValue(userJsonDescription.toString(), User.class);
		userId = aUser.getId().toString();
		MongoDBHandler.getInstance().store(aUser);

		TimeBox lifeSpan = new TimeBox(DateTime.parse("2015-10-07T10:14:40Z"), DateTime.parse("2015-10-10T11:22:40Z"));

		aGoal = new Goal(null, aChallenge, lifeSpan, aUser, null);
		goalId = aGoal.getId().toString();
		MongoDBHandler.getInstance().store(aGoal);

		goalJsonDescription = new JsonObject();
		goalJsonDescription.addProperty("user", userId);
		goalJsonDescription.addProperty("challenge", challengeId);
		goalJsonDescription.addProperty("id", goalId);
	}

	@AfterClass
	public static void eraseInDB() throws GoalNotFoundException, UserNotFoundException, ChallengeNotFoundException {
		// FIXME: 06/12/2015 not found MongoDBHandler.getInstance().deleteGoal(aGoal);
		MongoDBHandler.getInstance().deleteChallenge(aChallenge);
		MongoDBHandler.getInstance().deleteUser(aUser);
	}

	@Ignore
	@Test
	public void checkGoalCreationAndEvaluation() throws IOException, NotReadableElementException, IncoherentDBContentException, GoalNotFoundException, UserNotFoundException, NotSavableElementException {
		Model.getInstance().takeChallenge(goalJsonDescription);

		assertNull(aUser.getBadges());

		Model.getInstance().setTime(DateTime.now().plusDays(1));
		Model.getInstance().evaluate(userId, challengeId);

		aUser = MongoDBHandler.getInstance().readUserByID(userId);

		assertEquals(1, aUser.getBadges().size());

		Badge toCompare = new Badge("http://www.derp.com", 69, "you're too good for me");
		assertTrue(aUser.getBadges().contains(toCompare));


		Model.getInstance().setTime(DateTime.now().plusDays(2));
		Model.getInstance().evaluate(userId, challengeId);

		aUser = MongoDBHandler.getInstance().readUserByID(userId);

		assertEquals(2, aUser.getBadges().size());
	}
}
