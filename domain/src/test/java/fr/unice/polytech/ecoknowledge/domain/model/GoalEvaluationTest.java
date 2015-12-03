package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sébastien on 03/12/2015.
 */
public class GoalEvaluationTest {

	private static JsonObject jsonObject = null;
	private static JsonObject jsonObjectUser = null;
	private static JsonObject jsonObjectGoal = null;

	private static String challengeId = "challengeId";
	private static String userId = "userId";
	private static String goalId = "goalId";
	private static Challenge aChallenge = null;
	private static User aUser = null;
	private static Goal aGoal = null;

	private static Model m;

	@BeforeClass
	public static void loadJsonFile() throws IOException {

		jsonObject = TestUtils.getFakeJson(1);
		ObjectMapper objectMapper = new ObjectMapper();
		aChallenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		challengeId = aChallenge.getId() + "";
		challengeId = aChallenge.getId() + "";

		jsonObjectUser = TestUtils.getFakeUser(1);
		aUser = objectMapper.readValue(jsonObjectUser.toString(), User.class);
		userId = aUser.getId() + "";
	}

	@AfterClass
	public static void eraseInDB() {
		DataPersistence.drop(DataPersistence.Collections.CHALLENGE, challengeId);
		DataPersistence.drop(DataPersistence.Collections.USER, userId);
		DataPersistence.drop(DataPersistence.Collections.GOAL, goalId);
	}

	@Test
	public void checkGoalResearch_WithUserAndChallenge() throws IOException {

		aGoal = new Goal(null, aChallenge, new TimeBox(DateTime.now(), DateTime.now().plusDays(3)), aUser);
		goalId = aGoal.getId().toString();

		jsonObject.addProperty("id", challengeId);
		jsonObjectUser.addProperty("id", userId);
		jsonObjectGoal = new JsonObject();
		jsonObjectGoal.addProperty("user", userId);
		jsonObjectGoal.addProperty("challenge", challengeId);
		jsonObjectGoal.addProperty("id", goalId);

		DataPersistence.store(DataPersistence.Collections.CHALLENGE, jsonObject);
		DataPersistence.store(DataPersistence.Collections.USER, jsonObjectUser);

		m = new Model();
		aGoal = m.takeChallenge(jsonObjectGoal, new Clock());

		Clock clock = new Clock();

		assertEquals(userId, aGoal.getUser().getId().toString());
		assertEquals(
				clock.createDate(DateTime.now()).getDayOfYear(),
				clock.createDate(aGoal.getTimeSpan().getStart()).getDayOfYear());
		assertEquals(
				clock.createDate(aGoal.getTimeSpan().getStart()).getDayOfYear(),
				clock.createDate(aGoal.getTimeSpan().getEnd()).getDayOfYear());
		assertEquals(23, aGoal.getTimeSpan().getEnd().getHourOfDay());

		aGoal = m.getGoal(userId, challengeId);

		assertEquals(userId, aGoal.getUser().getId().toString());
		assertEquals(
				clock.createDate(DateTime.now()).getDayOfYear(),
				clock.createDate(aGoal.getTimeSpan().getStart()).getDayOfYear());
		assertEquals(
				clock.createDate(aGoal.getTimeSpan().getStart()).getDayOfYear(),
				clock.createDate(aGoal.getTimeSpan().getEnd()).getDayOfYear());
		assertEquals(23, aGoal.getTimeSpan().getEnd().getHourOfDay());
	}

	@Test
	public void evaluate() {
		// TODO
	}

}
